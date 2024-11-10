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

    // Data dummy untuk NIK yang terdaftar
    private static final String DUMMY_NIK_WARGA = "123456789";
    private static final String DUMMY_NIK_RT = "987654321";
    private static final String DUMMY_NIK_RW = "56789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_xreq);

        editTextNIK = findViewById(R.id.Verivikasi_NIK);
        buttonVerifikasi = findViewById(R.id.verivikasi_akun);

        buttonVerifikasi.setOnClickListener(v -> {
            String nik = editTextNIK.getText().toString();
            if (!nik.isEmpty()) {
                verifikasiNIK(nik);
            } else {
                Toast.makeText(AktivasiXreqActivity.this, "Masukkan NIK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifikasiNIK(String nik) {
        // Cek NIK di data dummy
        if (nik.equals(DUMMY_NIK_WARGA) || nik.equals(DUMMY_NIK_RT) || nik.equals(DUMMY_NIK_RW)) {
            // Jika NIK valid, lanjutkan ke halaman aktivasi
            Toast.makeText(AktivasiXreqActivity.this, "NIK valid, lanjutkan aktivasi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AktivasiXreqActivity.this, ActivasiActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Jika NIK tidak valid, arahkan ke halaman register
            Toast.makeText(AktivasiXreqActivity.this, "NIK tidak terdaftar, silakan registrasi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AktivasiXreqActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
