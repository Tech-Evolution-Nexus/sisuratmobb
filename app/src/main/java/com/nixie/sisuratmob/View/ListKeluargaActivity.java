package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ListKkAdapter;

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

public class ListKeluargaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListKkAdapter  listKeluargaAdapter;
    private List<ListKkModel> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_keluarga);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.reckk);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String idsurat = getIntent().getStringExtra("id_surat");
        dataList = new ArrayList<>();
        listKeluargaAdapter = new ListKkAdapter(getBaseContext(),dataList,idsurat);
        recyclerView.setAdapter(listKeluargaAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fungsi yang dijalankan saat ikon navigasi diklik
                onBackPressed(); // Misalnya kembali ke aktivitas sebelumnya
            }
        });
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("UserPrefs", getBaseContext().MODE_PRIVATE);
        String nokk = sharedPreferences.getString("nokk", "");
        fetchDataFromAPI(nokk);
    }
    private void fetchDataFromAPI(String nokk) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getkk(nokk);
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
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
                                ListKkModel listkk = new ListKkModel(
                                        dataObject.getString("nik"),
                                        dataObject.getString("nama_lengkap")
                                );

                                dataList.add(listkk);
                                listKeluargaAdapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(ListKeluargaActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }

//                    List<ListKkModel> suratList = response.body().getData().getDatakk();
//                    if (suratList != null) {
//                        Log.d("TAG", "onResponse: "+response.body().getData());
//                        dataList.clear();
//                        dataList.addAll(suratList);
//                        listKeluargaAdapter.notifyDataSetChanged();  // Refresh RecyclerView with new data
//                    } else {
//                        Toast.makeText(ListKeluargaActivity.this, "No data available", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(ListKeluargaActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(ListKeluargaActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}