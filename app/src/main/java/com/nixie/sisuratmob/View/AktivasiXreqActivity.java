package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Database.DbHelper; // Import DbHelper
import com.nixie.sisuratmob.R;

public class AktivasiXreqActivity extends AppCompatActivity {
    private TextInputEditText editTextNIK;
    private Button buttonVerifikasi;
    private DbHelper dbHelper; // Deklarasi DbHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_xreq);

        editTextNIK = findViewById(R.id.Verivikasi_NIK);
        buttonVerifikasi = findViewById(R.id.verivikasi_akun);
        dbHelper = new DbHelper(this); // Inisialisasi DbHelper

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
        // Cek NIK di database
        boolean isRegistered = checkNIKInDatabase(nik);

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
        return dbHelper.checkNIKExists(nik);
    }

}
