package com.nixie.sisuratmob.View;

import static android.widget.Toast.*;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformasiAkunActivity extends AppCompatActivity {
    private TextView infNamaLengkap, infNoKk, infNIK, infAlamat, infRT, infRW, infKelurahan, infKecamatan;
    private ImageView infimgKk;
    private static final int IMAGE_PICK_CODE = 1000;
    private int selectedPosition = -1;
    private String nik, idSurat;
    private List<BiodataModel> dataAkun = new ArrayList<BiodataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informasi_akun);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        infimgKk = findViewById(R.id.infimgKk);
        infNoKk = findViewById(R.id.infNoKk);

        infNamaLengkap = findViewById(R.id.infNamaLengkap);
        infNIK = findViewById(R.id.infNIK);
        infAlamat = findViewById(R.id.infAlamat);
        infRT = findViewById(R.id.infRT);
        infRW = findViewById(R.id.infRW);
        infKecamatan = findViewById(R.id.infKecamatan);
        infKelurahan = findViewById(R.id.infKelurahan);
        ImageView pp = findViewById(R.id.txt_homefotoprofil);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String foto = sharedPreferences.getString("foto", "");
        Glide.with(this)
                .load(Helpers.BASE_URL+"admin/assetsprofile/"+foto)
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(pp);

        fetchdata();
    }

    private void fetchdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nik = sharedPreferences.getString("nik", "");
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getinfouser(nik);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if (st) {

                            infNamaLengkap.setText(dataObject.getString("nama_lengkap"));
                            infNoKk.setText(dataObject.getString("no_kk"));
                            infNIK.setText(dataObject.getString("nik"));
                            infAlamat.setText(dataObject.getString("alamat"));
                            infRT.setText(dataObject.getString("rt"));
                            infRW.setText(dataObject.getString("rw"));
                            infKelurahan.setText(dataObject.getString("kelurahan"));
                            infKecamatan.setText(dataObject.getString("kecamatan"));
                            Glide.with(getBaseContext())
                                    .load(Helpers.BASE_URL + "admin/assets-kartu-keluarga/" + dataObject.getString("kk_file"))
                                    .into(infimgKk);

                        } else {
                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();


            }
        });
    }
}