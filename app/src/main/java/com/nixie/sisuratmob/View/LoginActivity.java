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
import com.google.firebase.messaging.FirebaseMessaging;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.UserLoginModel;
import com.nixie.sisuratmob.R;
import org.json.JSONObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNIK, editTextPassword;
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
            if (nik.isEmpty()) {
                editTextNIK.setError("NIK tidak boleh kosong");
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Password tidak boleh kosong");
                return;
            }
            if (nik.length() != 16) {
                editTextNIK.setError("NIK harus memiliki panjang 16 karakter");
                return;
            }
            if (password.length() < 8) {
                editTextPassword.setError("Password harus memiliki minimal 8 karakter");
                return;
            }

            UserLoginModel loginModel = new UserLoginModel(nik, password,token);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqLogin(loginModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
                                    editor.putString("usename", data.getString("role"));
                                    editor.putString("nokk", data.getString("no_kk"));
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
                    // Kesalahan koneksi server
                    Toast.makeText(LoginActivity.this, "Tidak dapat terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });



    }
}
