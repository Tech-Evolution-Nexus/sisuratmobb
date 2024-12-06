package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

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

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahEmailActivity extends AppCompatActivity {


    private TextInputEditText txtubahemail;
    private TextInputLayout txtinputubahemail;
    private Button btnubahemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubah_email);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtubahemail = findViewById(R.id.txtubahEmail);
        txtinputubahemail = findViewById(R.id.txtinputubuahemail);
        btnubahemail = findViewById(R.id.btn_ubahemail);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String nik = sharedPreferences.getString("nik", "");
        txtubahemail.setText(email);
        btnubahemail.setOnClickListener(v->{
            String emailbaru = txtubahemail.getText().toString();
            if (emailbaru.isEmpty()) {
                txtinputubahemail.setError("Email Tidak Boleh Kosong");
                txtinputubahemail.setErrorIconDrawable(null);
                return;
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailbaru).matches()) {
                txtinputubahemail.setError("Format email tidak valid");
                txtinputubahemail.setErrorIconDrawable(null);
                return;
            } else {
                txtinputubahemail.setError(null); // Menghapus pesan kesalahan jika valid
            }
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqUbahEmail(nik,emailbaru,email);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pDialog.dismissWithAnimation();
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject jsonObject = null;
                        try {
                            String responseBody = response.body().string();
                            jsonObject = new JSONObject(responseBody);
                            boolean st = jsonObject.getBoolean("status");
                            if(st){
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.putString("email", dataObject.getString("email"));
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
                    pDialog.dismissWithAnimation();
                }
            });


        });
    }

}