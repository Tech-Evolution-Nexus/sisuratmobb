package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.AktivasiModel;
import com.nixie.sisuratmob.R;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivasiActivity extends AppCompatActivity {

    private TextInputEditText textNik, textPassword, notelfon;
    private Button buttonActivasi;
    private TextView masuklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activasi);

        // Inisialisasi view
        masuklogin = findViewById(R.id.Activasi_log);
        textNik = findViewById(R.id.activasi_NIK);
        textPassword = findViewById(R.id.activasi_password);
        notelfon = findViewById(R.id.activasi_Nohp);
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
        buttonActivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = textNik.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String noTelpon = notelfon.getText().toString().trim();

                // Validasi input
                if (nik.isEmpty() || password.isEmpty() || noTelpon.isEmpty()) {
                    Toast.makeText(ActivasiActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Membuat model untuk aktivasi
               AktivasiModel userAktivasi = new AktivasiModel(nik, password, noTelpon);
                ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseBody> call = apiService.reqAktivasi(userAktivasi);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                // Ambil respons sebagai JSON
                                String responseBody = response.body().string();
                                JSONObject jsonObject = new JSONObject(responseBody);

                                // Ambil data dari JSON
                                JSONObject data = jsonObject.getJSONObject("data");
                                boolean status = data.getBoolean("status");
                                String message = data.getString("msg");

                                if (status) {
                                    // Aktivasi berhasil
                                    Toast.makeText(ActivasiActivity.this, "Akun berhasil diaktivasi!", Toast.LENGTH_SHORT).show();
                                    // Pindah ke halaman login
                                    startActivity(new Intent(ActivasiActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    // Aktivasi gagal
                                    Toast.makeText(ActivasiActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(ActivasiActivity.this, "Kesalahan parsing data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivasiActivity.this, "Aktivasi gagal, coba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ActivasiActivity.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
