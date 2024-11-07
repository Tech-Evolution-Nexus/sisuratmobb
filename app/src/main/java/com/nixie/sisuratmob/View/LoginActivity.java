package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Database.DbHelper;
import com.nixie.sisuratmob.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextNik;
    private TextInputEditText editTextPassword;
    private Button buttonlogin;
    private TextView textregister;
    private TextView txtforg;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Inisialisasi view
        editTextNik = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonlogin = findViewById(R.id.login_masuk);
        dbHelper = new DbHelper(this);
        txtforg = findViewById(R.id.textfor);

        // Set onclick listener untuk tombol login
        buttonlogin.setOnClickListener(v -> Login());

        // Inisialisasi dan set onclick listener untuk forgot password

        // Inisialisasi dan set onclick listener untuk registrasi
        txtforg.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        });
        textregister = findViewById(R.id.textregister);
        textregister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AktivasiXreqActivity.class);
            startActivity(intent);
        });
    }

    // Fungsi untuk pengiriman pesan
    private void Login() {
        String nik = editTextNik.getText().toString();
        String password = editTextPassword.getText().toString();

        // Tambahkan log untuk melihat nilai nik dan password
        Log.d("LoginActivity", "NIK: " + nik + ", Password: " + password);
        Toast.makeText(this, "login berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
//        if (dbHelper.checkUser(nik, password)) {
//            Toast.makeText(this, "login berhasil", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, DashboardActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(this, "nik dan password salah", Toast.LENGTH_SHORT).show();
//        }
    }

}
