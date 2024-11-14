package com.nixie.sisuratmob.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.VerivModel;
import com.nixie.sisuratmob.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktivasiXreqActivity extends AppCompatActivity {

    private TextInputEditText editTextNIK;
    private Button buttonVerifikasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_xreq);

        editTextNIK = findViewById(R.id.Verivikasi_NIK);
        buttonVerifikasi = findViewById(R.id.verivikasi_akun);

        buttonVerifikasi.setOnClickListener(v -> {
            String Nik = editTextNIK.getText().toString();
            VerivModel veriv = new VerivModel(Nik);
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.reqVerivikasi(veriv);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(AktivasiXreqActivity.this ,ActivasiActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(AktivasiXreqActivity.this,"Nik tidak terdaftar",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AktivasiXreqActivity.this,RegisterActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        });
    }
}
