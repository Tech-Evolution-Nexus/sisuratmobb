package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.textregister);
        lupaSandi = findViewById(R.id.textfor);
        editTextNIK = findViewById(R.id.Login_NIK);
        editTextPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_masuk);

        register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AktivasiXreqActivity.class);
            startActivity(intent);
        });
        lupaSandi.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            // Mendapatkan NIK dan Password dari input pengguna
            String nik = editTextNIK.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validasi input
            if (nik.isEmpty()) {
                Toast.makeText(LoginActivity.this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Membuat model login
            UserLoginModel loginModel = new UserLoginModel(nik, password);

            // Menggunakan Retrofit untuk mengirim request login
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqLogin(loginModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            // Mendapatkan respons API
                            String responseBody = response.body().string();
                            Log.d("API Response", responseBody);

                            // Validasi format respons JSON
                            if (responseBody.trim().startsWith("{")) {
                                JSONObject jsonObject = new JSONObject(responseBody);
                                JSONObject data = jsonObject.getJSONObject("data");

                                boolean status = data.getBoolean("status");
                                String message = data.getString("msg");

                                // Menangani jika login berhasil
                                if (status) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.putString("nik", data.getJSONObject("dataUserLogin").getString("nik"));
                                    editor.putString("role", data.getJSONObject("dataUserLogin").getString("role"));
                                    editor.apply();

                                    if(data.getJSONObject("dataUserLogin").getString("role").equals("masyarakat")){
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        finish();
                                    }else if(data.getJSONObject("dataUserLogin").getString("role").equals("rt")||data.getJSONObject("dataUserLogin").getString("role").equals("rw")){
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, DashboardRtActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Role Tidak Dizinkan", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    // Jika login gagal, menampilkan pesan kesalahan
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Jika respons bukan JSON yang valid
                                Log.e("API Error", "Respons bukan JSON: " + responseBody);
                                Toast.makeText(LoginActivity.this, "Kesalahan server: format respons tidak valid", Toast.LENGTH_SHORT).show();
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
                            String errorMessage = errorObject.getString("msg");
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
