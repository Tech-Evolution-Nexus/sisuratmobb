package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class ResetPaswordActivity extends AppCompatActivity {
    TextInputEditText foresetpass, foresetkonpass;
    TextInputLayout ltxtpass, lktxtpass;
    Button btnresetpass;
    private String email, token;
    private long expiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pasword);
        foresetpass = findViewById(R.id.foresetpass);
        foresetkonpass = findViewById(R.id.foresetkonpass);
        btnresetpass = findViewById(R.id.btnresetpass);

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) {
            // Extract the token from the query parameters
            String encodedToken = data.getQueryParameter("token");

            if (encodedToken != null) {
                // Decode the Base64 token
                String decodedToken = decodeBase64(encodedToken);

                if (decodedToken != null) {
                    try {
                        // Parse the decoded JSON string
                        JSONObject jsonObject = new JSONObject(decodedToken);

                        // Extract values from the JSON object
                        email = jsonObject.getString("email");
                        token = jsonObject.getString("token");
                        expiry = jsonObject.getLong("exp");

                        // Get the current time in seconds
                        long currentTime = System.currentTimeMillis() / 1000;

                        // Check if the token is expired
                        if (expiry < currentTime) {
                            // Token is expired, show popup and redirect to login
                            showTokenExpiredPopup();
                        } else {
                            // Token is still valid, proceed with further actions
                            Toast.makeText(this, "Valid Token. Proceeding...", Toast.LENGTH_LONG).show();
                            setResetPasswordClickListener();
                        }
                    } catch (JSONException e) {
                        // Handle JSON parsing error
                        Toast.makeText(this, "Error decoding token", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // If no token exists, redirect to the login screen
                redirectToLogin();
            }

        }
    }

    private String decodeBase64(String base64String) {
        try {
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            // Handle error if Base64 string is invalid
            Toast.makeText(this, "Invalid token", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // Show popup and redirect to login screen
    private void showTokenExpiredPopup() {
        new AlertDialog.Builder(this)
                .setTitle("Token Expired")
                .setMessage("Your reset token has expired. Please log in again.")
                .setPositiveButton("OK", (dialog, which) -> redirectToLogin())
                .setCancelable(false)
                .show();
    }

    // Redirect to login screen
    private void redirectToLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish(); // Close the current activity
    }

    private void setResetPasswordClickListener() {
        btnresetpass.setOnClickListener(view -> {
            String pass = foresetpass.getText().toString().trim();
            String konpass = foresetkonpass.getText().toString().trim();

            ltxtpass = findViewById(R.id.ltextpassForgot);
            lktxtpass = findViewById(R.id.ltextkPasswordForgot);
            boolean hasError = false;
            ltxtpass.setError(null);
            lktxtpass.setError(null);
            StringBuilder passErrors = new StringBuilder();
            StringBuilder kpasswordErrors = new StringBuilder();
            // Validate NIK
            if (pass.isEmpty()) {
                passErrors.append("Password tidak boleh kosong.\n");
                hasError = true;
            }
            if (pass.length() <8) {
                passErrors.append("Password Harus Memiliki Panjang Lebih Dari 8 Karakter.\n");
                hasError = true;
            }
            if (passErrors.length() > 0) {
                ltxtpass.setError(passErrors.toString().trim());
                ltxtpass.setErrorIconDrawable(null);
            }

            // Validate Password
            if (konpass.isEmpty()) {
                kpasswordErrors.append("Konfirmasi Password tidak boleh kosong.\n");
                hasError = true;
            }
            if (konpass.length() < 8) {
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
            if(!pass.equals(konpass)){
                lktxtpass.setError("Password Tidak Sama");
                lktxtpass.setErrorIconDrawable(null);
            }

            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            apiService.reqResetpass(email, token, pass).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String responseBody = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            boolean status = jsonObject.getBoolean("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(ResetPaswordActivity.this, message, Toast.LENGTH_SHORT).show();

                            if (status) {
                                Intent intent = new Intent(ResetPaswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            Toast.makeText(ResetPaswordActivity.this, "Error processing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ResetPaswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}