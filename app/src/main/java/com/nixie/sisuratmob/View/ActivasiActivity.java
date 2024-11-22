package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivasiActivity extends AppCompatActivity {

    private TextInputEditText textNik, textPassword, notelfon,textcPassword;
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
        textcPassword = findViewById(R.id.activasi_confirpasword);
        notelfon = findViewById(R.id.activasi_Nohp);
        buttonActivasi = findViewById(R.id.activasi_masuk);
        textNik.setText(getIntent().getStringExtra("nik"));

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
                String cpassword = textcPassword.getText().toString().trim();
                String noTelpon = notelfon.getText().toString().trim();

                // Validasi input
                if (nik.isEmpty()) {
                    textNik.setError("NIK tidak boleh kosong");
                    return;
                }

                if (password.isEmpty()) {
                    textPassword.setError("Password tidak boleh kosong");
                    return;
                }
                if (cpassword.isEmpty()) {
                    textcPassword.setError("Konfirmasi Password tidak boleh kosong");
                    return;
                }
                if (noTelpon.isEmpty()) {
                    notelfon.setError("no Telfon tidak boleh kosong");
                    return;
                }
                if (nik.length() != 16) {
                    textPassword.setError("NIK harus memiliki panjang 16 karakter");
                    return;
                }
                if (noTelpon.length() <=9||noTelpon.length()>=14) {
                    notelfon.setError("format no telfon salah");
                    return;
                }
                if (password.length() < 8) {
                    textPassword.setError("Password harus memiliki minimal 8 karakter");
                    return;
                }
                Log.d("TAG", "onClick: "+password+" "+cpassword);
                if (!password.equals(cpassword)) {
                    textcPassword.setError("Password Tidak sama");
                    return;
                }
               AktivasiModel userAktivasi = new AktivasiModel(nik, password, noTelpon);
                ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseBody> call = apiService.reqAktivasi(userAktivasi);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseBody = response.body().string();
                                JSONObject jsonObject = new JSONObject(responseBody);

                                // Ambil data dari JSON
                                boolean status = jsonObject.getBoolean("status");
                                String message = jsonObject.getString("message");

                                if (status) {
                                    Toast.makeText(ActivasiActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivasiActivity.this, LoginActivity.class);
                                    intent.putExtra("nik", nik);
                                    startActivity(intent);
                                    finish();
                                } else {
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
