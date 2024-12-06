package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.UserLoginModel;
import com.nixie.sisuratmob.R;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNIK, editTextPassword;
    private TextInputLayout lnik,lpass;
    private Button buttonLogin;
    private TextView register, lupaSandi;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>(){
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("as", "Fetching FCM registration token failed", task.getException());
                    return;
                }
                token= task.getResult();
            }
        });
        register = findViewById(R.id.textregister);
        lupaSandi = findViewById(R.id.textfor);
        editTextNIK = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_masuk);
        lnik = findViewById(R.id.Lnik);
        lpass = findViewById(R.id.Lpass);
        editTextNIK.setText(getIntent().getStringExtra("nik"));
        register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AktivasiXreqActivity.class);
            startActivity(intent);
        });
        lupaSandi.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            String nik = editTextNIK.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            boolean hasError = false;
            lnik.setError(null);
            lpass.setError(null);
            StringBuilder nikErrors = new StringBuilder();
            StringBuilder passwordErrors = new StringBuilder();
            // Validate NIK
            if (nik.isEmpty()) {
                nikErrors.append("NIK tidak boleh kosong.\n");
                hasError = true;
            }
            if (nik.length() != 16) {
                nikErrors.append("NIK harus memiliki panjang 16 karakter.\n");
                hasError = true;
            }
            if (nikErrors.length() > 0) {
                lnik.setError(nikErrors.toString().trim());
                lnik.setErrorIconDrawable(null);
            }

            // Validate Password
            if (password.isEmpty()) {
                passwordErrors.append("Password tidak boleh kosong.\n");
                hasError = true;
            }
            if (password.length() < 8) {
                passwordErrors.append("Password harus memiliki minimal 8 karakter.\n");
                hasError = true;
            }
            if (passwordErrors.length() > 0) {
                lpass.setError(passwordErrors.toString().trim());
                lpass.setErrorIconDrawable(null);
            }

            // Stop execution if there's any error
            if (hasError) {
                return;
            }
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
            UserLoginModel loginModel = new UserLoginModel(nik, password,token);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqLogin(loginModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pDialog.dismissWithAnimation();
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                                JSONObject jsonObject = new JSONObject(responseBody);
                                JSONObject data = jsonObject.getJSONObject("data");
                                boolean status = jsonObject.getBoolean("status");
                                String message = jsonObject.getString("message");
                                if (status) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.putString("nik", data.getString("nik"));
                                    editor.putString("role", data.getString("role"));
                                    editor.putString("namalengkap", data.getString("nama_lengkap"));
                                    editor.putString("nokk", data.getString("no_kk"));
                                    editor.putString("email", data.getString("email"));
                                    editor.putString("no_hp", data.getString("no_hp"));
                                    editor.apply();

                                    if(data.getString("role").equals("masyarakat")){
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        finish();
                                    }else if(data.getString("role").equals("rt")||data.getString("role").equals("rw")){
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, DashboardRtActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Role Tidak Dizinkan", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Kesalahan parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Jika respons gagal, menampilkan pesan error
                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject errorObject = new JSONObject(errorBody);
                            String errorMessage = errorObject.getString("message");
                            Toast.makeText(LoginActivity.this, "Login gagal: " + errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, "Tidak dapat terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
