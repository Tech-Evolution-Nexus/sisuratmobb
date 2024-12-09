package com.nixie.sisuratmob.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.View.Adapter.JsuratdashAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BritaallActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBerita;
    private TextView title,tgl,des;
    private ImageView img;
    private BeritaAdapter beritaAdapter;
    private List<Berita> dberitaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_britaall);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewBerita = findViewById(R.id.recbritaall);
        recyclerViewBerita = findViewById(R.id.recbritaall);
        recyclerViewBerita = findViewById(R.id.recbritaall);

        recyclerViewBerita = findViewById(R.id.recbritaall);
        recyclerViewBerita.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        dberitaList = new ArrayList<>();
        beritaAdapter = new BeritaAdapter(getBaseContext(), dberitaList);
        recyclerViewBerita.setAdapter(beritaAdapter);
//        recyclerViewBerita.setHasFixedSize(true);
        fetchdata();

    }
    private void fetchdata() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call2 = apiService.getberita("all");
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if(st){
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                Berita berita = new Berita(dataObject.getInt("id"),
                                        dataObject.getString("judul"),
                                        dataObject.getString("sub_judul"),
                                        dataObject.getString("deskripsi"),
                                        dataObject.getString("gambar"),
                                        dataObject.getString("created_at"));
                                dberitaList.add(berita);
                                beritaAdapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
//                    List<Berita> beritaList = response.body().getData().getDataberita();
//                    if (beritaList != null) {
//                        dberitaList.clear();
//                        dberitaList.addAll(beritaList);
//                    } else {
//                        Toast.makeText(getBaseContext(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getBaseContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getBaseContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}