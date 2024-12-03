package com.nixie.sisuratmob.View;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {
    private Button btn_pass;
    private TextInputEditText current_pass, new_pass, confirm_pass;
    private TextInputLayout txtinputubuahpasslama, txtinputubuahpassbaru, ctxtinputubuahpassbaru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubah_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_pass = findViewById(R.id.btn_pass);
        current_pass = findViewById(R.id.current_pass);
        new_pass = findViewById(R.id.new_password);
        confirm_pass = findViewById(R.id.confirm_pass);
        txtinputubuahpasslama = findViewById(R.id.txtinpustpasslama);
        txtinputubuahpassbaru = findViewById(R.id.txtinpustpassnew);
        ctxtinputubuahpassbaru = findViewById(R.id.ctxtinpustpassbaru);

        btn_pass.setOnClickListener(e -> {
            String pass_present = current_pass.getText().toString();
            String pass_now = new_pass.getText().toString();
            String pass_confirm = confirm_pass.getText().toString();
            StringBuilder passPresentErrors = new StringBuilder();
            StringBuilder passNowErrors = new StringBuilder();
            StringBuilder passConfirmErrors = new StringBuilder();
            boolean hasError = false;
            if (pass_present.isEmpty()) {
                passPresentErrors.append("Password saat ini tidak boleh kosong.\n");
                hasError = true;
            } else if (pass_present.length() < 8) {
                passPresentErrors.append("Password saat ini harus minimal 8 karakter.\n");
                hasError = true;
            }

// Validate Password baru (pass_now)
            if (pass_now.isEmpty()) {
                passNowErrors.append("Password baru tidak boleh kosong.\n");
                hasError = true;
            } else if (pass_now.length() < 8) {
                passNowErrors.append("Password baru harus minimal 8 karakter.\n");
                hasError = true;
            }

// Validate Konfirmasi password
            if (!pass_now.equals(pass_confirm)) {
                passConfirmErrors.append("Konfirmasi password tidak cocok.\n");
                hasError = true;
            }

// Set error messages if any
            if (passPresentErrors.length() > 0) {
                txtinputubuahpasslama.setError(passPresentErrors.toString().trim());
                txtinputubuahpasslama.setErrorIconDrawable(null);
            }
            if (passNowErrors.length() > 0) {
                txtinputubuahpassbaru.setError(passNowErrors.toString().trim());
                txtinputubuahpassbaru.setErrorIconDrawable(null);
            }
            if (passConfirmErrors.length() > 0) {
                ctxtinputubuahpassbaru.setError(passConfirmErrors.toString().trim());
                ctxtinputubuahpassbaru.setErrorIconDrawable(null);
            }

// Stop execution if there's any error
            if (hasError) {
                return;
            }

// All fields are valid
            txtinputubuahpasslama.setError(null);  // Remove error for valid pass_present
            txtinputubuahpassbaru.setError(null);  // Remove error for valid pass_now
            ctxtinputubuahpassbaru.setError(null);
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String nik = sharedPreferences.getString("nik", "");
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqgantiPassword(nik, pass_present, pass_now);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();


                }
            });


        });

    }


}