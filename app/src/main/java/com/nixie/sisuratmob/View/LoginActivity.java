package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.UserLoginModel; // Tambahkan import model UserLogin
import com.nixie.sisuratmob.R;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNIK, editTextPassword;
    private Button buttonLogin;
    private TextView register, lupaSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.textregister);
        lupaSandi = findViewById(R.id.textfor);

        editTextNIK = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_masuk);

        // Navigasi ke halaman registrasi
        register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AktivasiXreqActivity.class);
            startActivity(intent);
        });

        // Navigasi ke halaman lupa sandi
        lupaSandi.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Login
        buttonLogin.setOnClickListener(view -> {
            String nik = editTextNIK.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (nik.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "NIK dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }
            // Inisialisasi ApiService dan buat objek UserLoginModel
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            UserLoginModel userLoginModel = new UserLoginModel(nik, password);
            // Panggil endpoint login
            Call<ResponseBody> call = apiService.reqLogin(userLoginModel);
            // Menangani respons dari API
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Tangani respons sukses
                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish(); // Menutup halaman login
                    } else {
                        // Tangani respons gagal
                        Toast.makeText(LoginActivity.this, "Login gagal, periksa kembali NIK dan Password", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Tangani kesalahan koneksi atau server
                    Toast.makeText(LoginActivity.this, "Tidak dapat terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
