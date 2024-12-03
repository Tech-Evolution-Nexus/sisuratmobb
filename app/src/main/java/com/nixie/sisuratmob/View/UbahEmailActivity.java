package com.nixie.sisuratmob;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.databinding.ActivityUbahEmailBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahEmailActivity extends AppCompatActivity {


    private TextInputEditText txtubahemail;
    private Button btnubahemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtubahemail = findViewById(R.id.txtubahEmail);
        btnubahemail = findViewById(R.id.btn_ubahemail);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String nik = sharedPreferences.getString("nik", "");

        txtubahemail.setText(email);
        btnubahemail.setOnClickListener(v->{
            String emailbaru = txtubahemail.getText().toString();
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqUbahEmail(nik,emailbaru,email);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject jsonObject = null;
                        try {
                            String responseBody = response.body().string();
                            jsonObject = new JSONObject(responseBody);
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            boolean st = jsonObject.getBoolean("status");
                            if(st){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.putString("email", dataObject.getString("email"));
                                editor.apply();
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