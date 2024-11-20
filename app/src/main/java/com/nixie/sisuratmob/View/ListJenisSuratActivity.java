package com.nixie.sisuratmob.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.nixie.sisuratmob.View.Adapter.ListJenisSuratAdapter;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListJenisSuratActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListJenisSuratAdapter suratAdapter;
    private List<Surat> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_jenis_surat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recjsurat);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        suratAdapter = new ListJenisSuratAdapter(getBaseContext(),dataList);
        recyclerView.setAdapter(suratAdapter);
        TextInputEditText searchEditText = findViewById(R.id.carijsur);

        fetchDataFromAPI();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void fetchDataFromAPI(){
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getsurat();
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Surat> suratList = response.body().getData().getDatasurat();
                    if (suratList != null) {
                        dataList.clear();
                        dataList.addAll(suratList);
//                        filter("");
                        suratAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(ListJenisSuratActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListJenisSuratActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(ListJenisSuratActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filter(String text) {
        List<Surat> filteredList = new ArrayList<>();
        for (Surat surat : dataList) {
            if (surat.getNama_surat().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(surat);
            }
        }
        suratAdapter.updateList(filteredList);
    }


}