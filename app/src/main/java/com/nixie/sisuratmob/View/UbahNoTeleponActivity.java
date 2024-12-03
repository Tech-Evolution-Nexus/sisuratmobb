package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahNoTeleponActivity extends AppCompatActivity {
    private TextInputEditText ubah;
    private TextInputLayout txtInputUbahNoHP;
    private Button btnubahnoHP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubah_no_telepon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nohp = sharedPreferences.getString("no_hp", "");
        String nik = sharedPreferences.getString("nik", "");

        ubah = findViewById(R.id.txtubahNoHP);
        txtInputUbahNoHP = findViewById(R.id.txtinputuvahtlp);
        btnubahnoHP = findViewById(R.id.btn_ubahnoHP);
        ubah.setText(nohp);
        btnubahnoHP.setOnClickListener(v->{
            String nohpbaru = ubah.getText().toString();
            if (nohpbaru.isEmpty()) {
                txtInputUbahNoHP.setError("Nomor HP tidak boleh kosong");
                txtInputUbahNoHP.setErrorIconDrawable(null);
                return;
            } else if (!nohpbaru.matches("^08\\d{8,11}$")) {
                txtInputUbahNoHP.setError("Format nomor HP tidak valid (harus dimulai dengan 08 dan 10-13 digit)");
                txtInputUbahNoHP.setErrorIconDrawable(null);
                return;
            } else {
                txtInputUbahNoHP.setError(null);  // Hapus error jika valid
            }
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqUbahNohp(nik,nohp,nohpbaru);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject jsonObject = null;
                        try {
                            String responseBody = response.body().string();
                            jsonObject = new JSONObject(responseBody);
                            boolean st = jsonObject.getBoolean("status");
                            if(st){
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("no_hp");
                                editor.putString("no_hp", dataObject.getString("no_hp"));
                                editor.apply();
                                finish();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        });
    }
}