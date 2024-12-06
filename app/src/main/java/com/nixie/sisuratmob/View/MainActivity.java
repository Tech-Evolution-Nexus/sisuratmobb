package com.nixie.sisuratmob.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nixie.sisuratmob.R;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private String[] REQUIRED_PERMISSIONS;
    private boolean allPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tentukan izin berdasarkan versi SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES
            };
        } else {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        // Cek apakah semua izin telah diberikan
        if (!hasAllPermissions()) {
            requestPermissions();
        } else {
            // Jika izin sudah ada, lanjutkan ke alur berikutnya
            navigateAfterPermission();
        }
    }

    // Memeriksa apakah semua izin yang diperlukan telah diberikan
    private boolean hasAllPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Meminta izin yang diperlukan
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    // Menangani hasil dari permintaan izin
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            boolean allGranted = true;
            for (int i = 0; i < grantResults.length; i++) {
                Log.d("TAG", "Permission result for " + permissions[i] + ": " + grantResults[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    String deniedPermission = permissions[i];
                    Toast.makeText(this, "Izin ditolak: " + deniedPermission, Toast.LENGTH_SHORT).show();
                }
            }
            allPermissionsGranted = allGranted;

            if (allGranted) {
                Toast.makeText(this, "Semua izin diberikan!", Toast.LENGTH_SHORT).show();
                // Lanjutkan navigasi setelah izin diberikan
                navigateAfterPermission();
            } else {
                showPermissionRationale();
            }
        }
    }

    private void showPermissionRationale() {
        new AlertDialog.Builder(this)
                .setTitle("Izin Diperlukan")
                .setMessage("Aplikasi memerlukan izin ini agar dapat berjalan dengan baik. Silakan berikan izin.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                .setNegativeButton("Pengaturan", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                })
                .show();
    }

    // Navigasi ke activity berdasarkan status login dan role
    private void navigateAfterPermission() {
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            String role = sharedPreferences.getString("role", "");

            if (!isLoggedIn) {
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
                finish();
            } else {
                if ("masyarakat".equals(role)) {
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                } else if ("rt".equals(role) || "rw".equals(role)) {
                    startActivity(new Intent(MainActivity.this, DashboardRtActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, TutorialActivity.class));
                }
                finish();
            }
        }, 5000);
    }
}
