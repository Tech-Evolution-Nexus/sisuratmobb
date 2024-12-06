package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Database.DbHelper;
import com.nixie.sisuratmob.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText forgotemail;
    private Button btn;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        forgotemail = findViewById(R.id.textemailpass);
        btn = findViewById(R.id.btnsendemail);
        dbHelper = new DbHelper(this);
        btn.setOnClickListener(v -> forgot());
    }

    public void forgot() {
        String email = forgotemail.getText().toString().trim();
        StringBuilder emailErrors = new StringBuilder();
        TextInputLayout txt = findViewById(R.id.texmailpass);
        boolean hasError = false;
        txt.setError(null);
        if (email.isEmpty()) {
            emailErrors.append("Email Harus Diisi.\n");
            hasError = true;
        }
        if( !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") ){
            emailErrors.append("Masukkan format email yang valid.\n");
            hasError = true;
        }
        if (emailErrors.length() > 0) {
            txt.setError(emailErrors.toString().trim());
            txt.setErrorIconDrawable(null);
        }
        if (hasError) {
            return;
        }
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getresetpassRetrofitInstance().create(ApiService.class);
        apiService.reqSendEmail(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        boolean status = jsonObject.getBoolean("status");
                        String message = jsonObject.getString("message");
                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (status) {
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
//                            intent.putExtra("nik", nik);
                            startActivity(intent);
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
                Toast.makeText(ForgotPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
