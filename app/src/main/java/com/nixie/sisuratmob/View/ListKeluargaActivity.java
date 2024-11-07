package com.nixie.sisuratmob.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ListKkAdapter;

import java.util.ArrayList;
import java.util.List;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();
        listKeluargaAdapter = new ListKkAdapter(dataList);
        recyclerView.setAdapter(listKeluargaAdapter);

        // Mengambil data dari API
        fetchDataFromAPI("1232313212133212");
    }
    private void fetchDataFromAPI(String nokk) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getkk(nokk);
        call.enqueue(new Callback<ResponModel>(){
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ListKkModel> suratList = response.body().getDatakk();
                    if (suratList != null) {
                        dataList.clear();
                        dataList.addAll(suratList);
                        listKeluargaAdapter.notifyDataSetChanged();  // Refresh RecyclerView with new data
                    } else {
                        Toast.makeText(ListKeluargaActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListKeluargaActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(ListKeluargaActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}