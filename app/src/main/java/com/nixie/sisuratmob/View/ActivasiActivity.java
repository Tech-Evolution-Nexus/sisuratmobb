package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.R;

public class ActivasiActivity extends AppCompatActivity {

    private TextInputEditText textNik, textPassword;
    private Button buttonActivasi;
    private TextView masuklogin;

    // Data dummy untuk NIK yang terdaftar
    private static final String DUMMY_NIK_WARGA = "123456789";
    private static final String DUMMY_NIK_RT = "987654321";
    private static final String DUMMY_NIK_RW = "56789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activasi);

        // Inisialisasi view
        masuklogin = findViewById(R.id.Activasi_log);
        textNik = findViewById(R.id.activasi_NIK);
        textPassword = findViewById(R.id.activasi_password);
        buttonActivasi = findViewById(R.id.activasi_masuk);

        // Listener untuk masuk login
        masuklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivasiActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Listener untuk tombol aktivasi
        buttonActivasi.setOnClickListener(v -> {
            String nik = textNik.getText().toString();
            String password = textPassword.getText().toString();

            if (nik.isEmpty() || password.isEmpty()) {
                Toast.makeText(ActivasiActivity.this, "NIK dan Password harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                // Cek apakah NIK sudah terdaftar
                if (nik.equals(DUMMY_NIK_WARGA) || nik.equals(DUMMY_NIK_RT) || nik.equals(DUMMY_NIK_RW)) {
                    Toast.makeText(ActivasiActivity.this, "NIK valid, Aktivasi Berhasil", Toast.LENGTH_SHORT).show();
                    // Arahkan ke LoginActivity setelah aktivasi berhasil
                    Intent intent = new Intent(ActivasiActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivasiActivity.this, "NIK tidak terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
