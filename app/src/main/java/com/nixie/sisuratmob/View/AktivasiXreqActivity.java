package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.R;

public class AktivasiXreqActivity extends AppCompatActivity {
    private TextInputEditText editTextNIK;
    private Button buttonVerifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_xreq);

        editTextNIK = findViewById(R.id.Verivikasi_NIK);
        buttonVerifikasi = findViewById(R.id.verivikasi_akun);

        buttonVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = editTextNIK.getText().toString();
                if (!nik.isEmpty()) {
                    verifikasiNIK(nik);
                } else {
                    Toast.makeText(AktivasiXreqActivity.this, "Masukkan NIK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifikasiNIK(String nik) {
        // Simulasi pengecekan NIK dari database
        boolean isRegistered = checkNIKInDatabase(nik); // Anda perlu mengimplementasikan ini

        if (isRegistered) {
            // Jika NIK terdaftar
            Toast.makeText(this, "NIK terdaftar, lanjut ke Aktivasi Akun", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AktivasiXreqActivity.this, ActivasiActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Jika NIK belum terdaftar
            Toast.makeText(this, "NIK tidak terdaftar, silakan registrasi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AktivasiXreqActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean checkNIKInDatabase(String nik) {
        // Logika untuk memeriksa NIK di database
        // Gantilah dengan logika yang sesuai untuk aplikasi Anda
        // Misalnya menggunakan API, SQLite, atau Firebase

        // Contoh statis
        return nik.equals("1234567890123456"); // Ganti dengan logika sebenarnya
    }
}