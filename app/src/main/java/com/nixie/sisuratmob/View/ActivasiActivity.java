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
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;

import java.io.IOException;

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
        buttonActivasi.setOnClickListener(v -> {
            String nik = textNik.getText().toString();
            String no_hp = notelfon.getText().toString();
            String password = textPassword.getText().toString();
            AktivasiModel aktiv = new AktivasiModel(nik,no_hp,password);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqAKtivasi(aktiv);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(ActivasiActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ActivasiActivity.this,"Gagal Aktivasi",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            });



    }
}
