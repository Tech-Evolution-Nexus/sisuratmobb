package com.nixie.sisuratmob.View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Target;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBritaActivity extends AppCompatActivity {
    TextView title,sub, tgl;
    WebView des;
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
        des = findViewById(R.id.webview);
        img = findViewById(R.id.detailimage);
        sub = findViewById(R.id.subjudl);

        String idBerita = getIntent().getStringExtra("id_berita");
        fetchdata(idBerita);
    }

    private void fetchdata(String idberita) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call2 = apiService.getdetailberita(Integer.parseInt(idberita));
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        String responseBody = response.body().string();
                        Log.d("Response Body", responseBody);
                        JSONObject jsonObject =  new JSONObject(responseBody);
                        JSONObject dataArray = jsonObject.getJSONObject("data");
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if(st){
                            title.setText(dataArray.getString("judul"));
                            tgl.setText(Helpers.formatTanggalalltime(dataArray.getString("created_at")));
                            sub.setText(dataArray.getString("sub_judul"));

//                            des.setText();
                            String imageUrl = Helpers.BASE_URL + "admin/assetsberita/" + dataArray.getString("gambar");
                            Glide.with(DetailBritaActivity.this)
                                    .load(imageUrl)
                                    .override(1000, 1000) // Ubah ukuran sesuai kebutuhan
                                    .placeholder(R.drawable.baground_rtrw)
                                    .error(R.drawable.baground_rtrw)
                                    .into(img);
                            des.loadData(dataArray.getString("deskripsi"), "text/html", "UTF-8");
                        }else{
                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                        }
//                    List<Berita> beritaList = response.body().getData().getDataberita();


                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }


                } else {
                    Toast.makeText(getBaseContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getBaseContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}