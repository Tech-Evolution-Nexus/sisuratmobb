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
import com.nixie.sisuratmob.Models.VerivModel;
import com.nixie.sisuratmob.R;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktivasiXreqActivity extends AppCompatActivity {

    private TextInputEditText textNik;
    private Button buttonVerifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_xreq);

        textNik = findViewById(R.id.Verivikasi_NIK);
        buttonVerifikasi = findViewById(R.id.verivikasi_akun);

        // Listener untuk masuk login

        // Listener untuk tombol verifikasi
        buttonVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = textNik.getText().toString().trim();

                // Validasi input
                if (nik.isEmpty()) {
                    Toast.makeText(AktivasiXreqActivity.this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Membuat model untuk verifikasi
            VerivModel veriv = new VerivModel(nik);
                ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseBody> call = apiService.reqVerifikasi(veriv);

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
                                    // Verifikasi berhasil
                                    Toast.makeText(AktivasiXreqActivity.this, "Verifikasi berhasil, akun aktif", Toast.LENGTH_SHORT).show();
                                    // Pindah ke halaman login
                                    Intent intent = new Intent(AktivasiXreqActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Verifikasi gagal
                                    Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(AktivasiXreqActivity.this, "Kesalahan parsing data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AktivasiXreqActivity.this, "Verifikasi gagal, coba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(AktivasiXreqActivity.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
