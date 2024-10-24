package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
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
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextNik = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonlogin = findViewById(R.id.login_masuk);
        dbHelper = new DbHelper(this);
        buttonlogin.setOnClickListener(v -> Login());
        textregister = findViewById(R.id.textregister);
        TextView textregister = findViewById(R.id.textregister); // Pastikan ID ini sesuai dengan layout XML

        textregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AktivasiXreqActivity.class);
                startActivity(intent);
            }
        }); // Tutup setOnClickListener dengan benar
    }
        //fungsi untuk pengiriman pesan
        private void Login () {
            String nik = editTextNik.getText().toString();
            String password = editTextPassword.getText().toString();

            if (dbHelper.checkUser(nik, password)) {
                Toast.makeText(this, "login berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "nik dan password salah", Toast.LENGTH_SHORT).show();
            }
        }

    }
