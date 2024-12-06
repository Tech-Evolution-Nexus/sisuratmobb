package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.AktivasiModel;
import com.nixie.sisuratmob.R;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivasiActivity extends AppCompatActivity {

    private TextInputEditText textNik, textPassword, notelfon, textcPassword, textEmail;
    private TextInputLayout ltxtnik, ltxtnohp, ltxtpass, lktxtpass, lemail;
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
        textEmail = findViewById(R.id.activasi_email);

        ltxtnik = findViewById(R.id.textactivasi1);
        ltxtnohp = findViewById(R.id.textactivasi2);
        ltxtpass = findViewById(R.id.textactivasi3);
        lktxtpass = findViewById(R.id.textactivasi4);
        lemail = findViewById(R.id.textactivas62);

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
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String cpassword = textcPassword.getText().toString().trim();
                String noTelpon = notelfon.getText().toString().trim();
                boolean hasError = false;
                StringBuilder nikErrors = new StringBuilder();
                StringBuilder emailErrors = new StringBuilder();
                StringBuilder nohpErrors = new StringBuilder();
                StringBuilder passErrors = new StringBuilder();
                StringBuilder kpasswordErrors = new StringBuilder();
                ltxtnik.setError(null);
                ltxtnohp.setError(null);
                ltxtpass.setError(null);
                lktxtpass.setError(null);
                lemail.setError(null);

                if (nik.isEmpty()) {
                    nikErrors.append("NIK tidak boleh kosong.\n");
                    hasError = true;
                }
                if (nik.length() != 16) {
                    nikErrors.append("NIK Harus Memiliki Panjang 16 Karakter.\n");
                    hasError = true;
                }
                if (nikErrors.length() > 0) {
                    ltxtnik.setError(nikErrors.toString().trim());
                    ltxtnik.setErrorIconDrawable(null);
                }

                if (email.isEmpty()) {
                    emailErrors.append("Email tidak boleh kosong.\n");
                    hasError = true;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailErrors.append("Format email tidak valid.\n");
                    hasError = true;
                }
                if (emailErrors.length() > 0) {
                    lemail.setError(emailErrors.toString().trim());
                    lemail.setErrorIconDrawable(null);
                }
                if (noTelpon.isEmpty()) {
                    nohpErrors.append("No Telpon tidak boleh kosong.\n");
                    hasError = true;
                }
                if (noTelpon.length() <= 11 || noTelpon.length() > 13) {
                    nohpErrors.append("No Telpon Harus Memiliki Panjang Lebih Dari 11 Dan Kurang Dari 13 Karakter.\n");
                    hasError = true;
                }
                if (nohpErrors.length() > 0) {
                    ltxtnohp.setError(nohpErrors.toString().trim());
                    ltxtnohp.setErrorIconDrawable(null);
                }

                if (password.isEmpty()) {
                    passErrors.append("Password tidak boleh kosong.\n");
                    hasError = true;
                }
                if (password.length() < 8) {
                    passErrors.append("Password Harus Memiliki Panjang Lebih Dari 8 Karakter.\n");
                    hasError = true;
                }
                if (passErrors.length() > 0) {
                    ltxtpass.setError(passErrors.toString().trim());
                    ltxtpass.setErrorIconDrawable(null);
                }

                // Validate Password
                if (cpassword.isEmpty()) {
                    kpasswordErrors.append("Konfirmasi Password tidak boleh kosong.\n");
                    hasError = true;
                }
                if (cpassword.length() < 8) {
                    kpasswordErrors.append("Password Harus Memiliki Panjang Lebih Dari 8 Karakter.\n");
                    hasError = true;
                }
                if (kpasswordErrors.length() > 0) {
                    lktxtpass.setError(kpasswordErrors.toString().trim());
                    lktxtpass.setErrorIconDrawable(null);
                }

                // Stop execution if there's any error
                if (hasError) {
                    return;
                }
                if (!password.equals(cpassword)) {
                    lktxtpass.setError("Password Tidak Sama");
                    lktxtpass.setErrorIconDrawable(null);
                }

                if (!password.equals(cpassword)) {
                    textcPassword.setError("Password Tidak sama");
                    return;
                }
                new SweetAlertDialog(getBaseContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Anda yakin ingin melanjutkan?")
                        .setConfirmText("Ya")
                        .setCancelText("Tidak")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#4CAF50")) // Tombol Yes (Hijau)
                        .setCancelButtonBackgroundColor(Color.parseColor("#F44336")) // Tombol No (Merah)
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            SweetAlertDialog pDialog = new SweetAlertDialog(getBaseContext(), SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.setTitleText("Loading...");
                            pDialog.setCancelable(false);
                            pDialog.show();
                            AktivasiModel userAktivasi = new AktivasiModel(nik, email, password, noTelpon);
                            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
                            Call<ResponseBody> call = apiService.reqAktivasi(userAktivasi);

                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    pDialog.dismissWithAnimation();
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
                                    pDialog.dismissWithAnimation();
                                    Toast.makeText(ActivasiActivity.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            // Tambahkan logika untuk No di sini
                        })
                        .show();

            }
        });
    }
}
