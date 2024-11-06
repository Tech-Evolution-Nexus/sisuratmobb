package com.nixie.sisuratmob.View.Warga.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Database.DbHelper;
import com.nixie.sisuratmob.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText forgotnik;
    private TextInputEditText forgotpass;
    private Button forgot_akun;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);


        forgotnik = findViewById(R.id.forgot_NIK);
        forgotpass = findViewById(R.id.forgot_Password);
        forgot_akun = findViewById(R.id.forgot_akun);
        dbHelper = new DbHelper(this);


        forgot_akun.setOnClickListener(v -> forgot());
    }

    public void forgot() {
        String nik = forgotnik.getText().toString().trim();
        String password = forgotpass.getText().toString().trim();

        if (nik.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "semu kolom wajib diisi", Toast.LENGTH_SHORT);
            return;
        }

        if (dbHelper.checkNIKExists(nik)) {
            boolean isUpdated = dbHelper.updatePasswordByNIK(nik, password);
            if (isUpdated) {
                Toast.makeText(this, "Kata sandi berhasil dirubah", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal mengubah kata sandi", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "NIK tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

    }

}
