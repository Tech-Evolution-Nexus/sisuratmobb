package com.nixie.sisuratmob.View.Warga.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.RT.DashboardRtActivity;
import com.nixie.sisuratmob.View.RW.DashboardRwActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNIK, editTextPassword;
    private Button buttonLogin;

    // Data dummy untuk login (NIK dan Password berdasarkan role)
    private static final String DUMMY_NIK_WARGA = "123456789";
    private static final String DUMMY_PASSWORD_WARGA = "123";
    private static final String DUMMY_NIK_RT = "987654321";
    private static final String DUMMY_PASSWORD_RT = "321";
    private static final String DUMMY_NIK_RW = "56789";
    private static final String DUMMY_PASSWORD_RW = "rw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextNIK = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_masuk);

        buttonLogin.setOnClickListener(v -> {
            String nik = editTextNIK.getText().toString();
            String password = editTextPassword.getText().toString();
            if (!nik.isEmpty() && !password.isEmpty()) {
                login(nik, password);
            } else {
                Toast.makeText(LoginActivity.this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String nik, String password) {
        if (nik.equals(DUMMY_NIK_WARGA) && password.equals(DUMMY_PASSWORD_WARGA)) {
            Toast.makeText(LoginActivity.this, "Login Berhasil sebagai Warga", Toast.LENGTH_SHORT).show();
            // Arahkan ke Dashboard Warga
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else if (nik.equals(DUMMY_NIK_RT) && password.equals(DUMMY_PASSWORD_RT)) {
            Toast.makeText(LoginActivity.this, "Login Berhasil sebagai RT", Toast.LENGTH_SHORT).show();
            // Arahkan ke Dashboard RT
            Intent intent = new Intent(LoginActivity.this, DashboardRtActivity.class);
            startActivity(intent);
            finish();
        } else if (nik.equals(DUMMY_NIK_RW) && password.equals(DUMMY_PASSWORD_RW)) {
            Toast.makeText(LoginActivity.this, "Login Berhasil sebagai RW", Toast.LENGTH_SHORT).show();
            // Arahkan ke Dashboard RW
            Intent intent = new Intent(LoginActivity.this, DashboardRwActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "NIK atau password salah", Toast.LENGTH_SHORT).show();
        }
    }
}
