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
                textNik.setError("NIK tidak boleh kosong");
                return;
            }
            if (textNik.length()!=16) {
                textNik.setError("NIK harus memiliki panjang 16 karakter");
                return;
            }

            VerivModel verivModel = new VerivModel(nik);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqVerifikasi(verivModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()&& response.body() != null) {
                        try {
                            // Mendapatkan respons API
                            String responseBody = response.body().string();
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                            if (!jsonObject.isNull("status")) {
                                boolean status = jsonObject.getBoolean("status");

                                if (status) {
                                    // Jika status true, arahkan ke RegisterActivity
                                    Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AktivasiXreqActivity.this, ActivasiActivity.class);
                                    intent.putExtra("nik", nik);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Jika status false, arahkan ke ActivasiActivity
                                    Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AktivasiXreqActivity.this, RegisterActivity.class);
                                    intent.putExtra("nik", nik);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Jika status null, tidak ada tindakan (hanya tampilkan pesan)
                                Toast.makeText(AktivasiXreqActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AktivasiXreqActivity.this, "Kesalahan parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
