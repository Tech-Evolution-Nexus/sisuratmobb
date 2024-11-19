package com.nixie.sisuratmob.View;

import android.app.ProgressDialog;
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
import com.nixie.sisuratmob.Models.UserLoginModel;
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

        buttonVerifikasi.setOnClickListener(view -> {
            String nik = textNik.getText().toString().trim();

            if (nik.isEmpty()) {
                Toast.makeText(AktivasiXreqActivity.this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            VerivModel verivModel = new VerivModel(nik);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqVerifikasi(verivModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            // Mendapatkan respons API
                            String responseBody = response.body().string();
                            Log.d("API Response", responseBody);

                            // Validasi format respons
                            if (responseBody.trim().startsWith("{")) {
                                JSONObject jsonObject = new JSONObject(responseBody);
                                JSONObject data = jsonObject.getJSONObject("data");

                                boolean status = data.getBoolean("status");
                                String message = data.getString("msg");

                                // Menangani jika NIK ditemukan
                                if (status) {
                                    Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AktivasiXreqActivity.this, ActivasiActivity.class));
                                    finish();
                                } else {
                                    // Jika NIK tidak ditemukan, arahkan ke RegisterActivity
                                    Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AktivasiXreqActivity.this, RegisterActivity.class));
                                    finish();
                                }
                            } else {
                                // Jika respons bukan JSON yang valid
                                Log.e("API Error", "Respons bukan JSON: " + responseBody);
                                Toast.makeText(AktivasiXreqActivity.this, "Kesalahan server: format respons tidak valid", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AktivasiXreqActivity.this, "Kesalahan parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Jika respons gagal, menampilkan pesan error
                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject errorObject = new JSONObject(errorBody);
                            String errorMessage = errorObject.getString("msg");
                            Toast.makeText(AktivasiXreqActivity.this, "Verifikasi gagal: " + errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AktivasiXreqActivity.this, "Verifikasi gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Kesalahan koneksi server
                    Toast.makeText(AktivasiXreqActivity.this, "Tidak dapat terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
