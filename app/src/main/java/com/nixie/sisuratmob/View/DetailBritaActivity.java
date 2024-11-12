package com.nixie.sisuratmob.View;

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

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBritaActivity extends AppCompatActivity {
    TextView title,tgl,des;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_brita);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        title = findViewById(R.id.titleberitadetail);
        tgl = findViewById(R.id.tglberitadetail);
        des = findViewById(R.id.deskripsidetail);
        img = findViewById(R.id.detailimage);


        String idBerita = getIntent().getStringExtra("id_berita");
        fetchdata(idBerita);
    }

    private void fetchdata(String idberita) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call2 = apiService.getdetailberita(Integer.parseInt(idberita));
        call2.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Berita> beritaList = response.body().getData().getDataberita();
                    if (beritaList != null) {
                        Berita berita = beritaList.get(0);
                        title.setText(berita.getJudul());
                        tgl.setText(berita.getCreated_at());
                        des.setText(berita.getDeskripsi());

                        Glide.with(DetailBritaActivity.this)
                                .load("http://192.168.100.205/SISURAT/admin/assetsberita/"+berita.getGambar())
                                .placeholder(R.drawable.baground_rtrw)
                                .error(R.drawable.baground_rtrw)
                                .into(img);

                    } else {
                        Toast.makeText(getBaseContext(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getBaseContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}