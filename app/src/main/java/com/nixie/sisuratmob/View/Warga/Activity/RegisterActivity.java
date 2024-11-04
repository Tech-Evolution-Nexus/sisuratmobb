package com.nixie.sisuratmob.View.Warga.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Models.Warga.RegistrasiModel;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText Editnik;
    private TextInputEditText Editnamalengkap;
    private TextInputEditText Editjk;
    private TextInputEditText Edittempatlahir;
    private TextInputEditText Edittanggal;
    private TextInputEditText Editagama;
    private TextInputEditText Editpendidikan;
    private TextInputEditText Editpekerjaan;
    private TextInputEditText Editgoldarah;
    private TextInputEditText Editstatusperkawinan;
    private TextInputEditText Editstatuskeluarga;
    private TextInputEditText Editkwarganegaraan;
    private TextInputEditText Editnamaayah;
    private TextInputEditText Editnamaibu;
    private TextInputEditText Editemail;
    private TextInputEditText Editnohp;
    private TextInputEditText Editpassword;
    private TextInputEditText Editconfirm;

    private Button btn_register;

    // List untuk menyimpan data pengguna dummy sementara
    private List<RegistrasiModel> dummyUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Inisialisasi EditText dan Button
        Editnik = findViewById(R.id.registrasi_NIK);
        Editnamalengkap = findViewById(R.id.registrasi_nama_lengkap);
        Editjk = findViewById(R.id.registrasi_jenis_kelamin);
        Edittempatlahir = findViewById(R.id.registrasi_tempat_lahir);
        Edittanggal = findViewById(R.id.registrasi_tgl_lahir);
        Editagama = findViewById(R.id.registrasi_agama);
        Editpendidikan = findViewById(R.id.registrasi_pendidikan);
        Editpekerjaan = findViewById(R.id.registrasi_pekerjaan);
        Editgoldarah = findViewById(R.id.registrasi_golongan_darah);
        Editstatusperkawinan = findViewById(R.id.registrasi_status_perkawinan);
        Editstatuskeluarga = findViewById(R.id.registrasi_status_keluarga);
        Editkwarganegaraan = findViewById(R.id.registrasi_kewarganegaraan);
        Editnamaayah = findViewById(R.id.registrasi_nama_ayah);
        Editnamaibu = findViewById(R.id.registrasi_nama_ibu);
        Editemail = findViewById(R.id.registrasi_email);
        Editnohp = findViewById(R.id.registrasi_no_hp);
        Editpassword = findViewById(R.id.registrasi_password);
        Editconfirm = findViewById(R.id.registrasi_confirpasword);
        btn_register = findViewById(R.id.registrasi_masuk);

        btn_register.setOnClickListener(v -> register());
    }

    // Metode untuk melakukan registrasi
    public void register() {
        String nik = Editnik.getText().toString();
        String nama_lengkap = Editnamalengkap.getText().toString();
        String jenis_kelamin = Editjk.getText().toString();
        String tempat_lahir = Edittempatlahir.getText().toString();
        String tgl_lahir = Edittanggal.getText().toString();
        String agama = Editagama.getText().toString();
        String pendidikan = Editpendidikan.getText().toString();
        String pekerjaan = Editpekerjaan.getText().toString();
        String golongan_darah = Editgoldarah.getText().toString();
        String status_perkawinan = Editstatusperkawinan.getText().toString();
        String status_keluarga = Editstatuskeluarga.getText().toString();
        String kewarganegaraan = Editkwarganegaraan.getText().toString();
        String nama_ayah = Editnamaayah.getText().toString();
        String nama_ibu = Editnamaibu.getText().toString();
        String email = Editemail.getText().toString();
        String no_hp = Editnohp.getText().toString();
        String password = Editpassword.getText().toString();
        String confirm_password = Editconfirm.getText().toString();

        // Validasi input
        if (nik.isEmpty() || nama_lengkap.isEmpty() || jenis_kelamin.isEmpty() || tempat_lahir.isEmpty() ||
                tgl_lahir.isEmpty() || agama.isEmpty() || pendidikan.isEmpty() || pekerjaan.isEmpty() ||
                golongan_darah.isEmpty() || status_perkawinan.isEmpty() || status_keluarga.isEmpty() ||
                kewarganegaraan.isEmpty() || nama_ayah.isEmpty() || nama_ibu.isEmpty() || email.isEmpty() ||
                password.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirm_password)) {
            Toast.makeText(this, "Kata sandi dan Confirm password harus sama", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek RegistrasiModel
        RegistrasiModel registrasiModel = new RegistrasiModel(0, nik, nama_lengkap, jenis_kelamin, tempat_lahir,
                tgl_lahir, agama, pendidikan, pekerjaan, golongan_darah, status_perkawinan, status_keluarga,
                kewarganegaraan, nama_ayah, nama_ibu, email, password, no_hp);

        // Tambah data ke list dummy
        dummyUserList.add(registrasiModel);
        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();

        // Pindah ke LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}