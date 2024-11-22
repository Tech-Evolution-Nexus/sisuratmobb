package com.nixie.sisuratmob.View;

import android.annotation.SuppressLint;
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
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ListJenisSuratAdapter;

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

        suratAdapter = new ListJenisSuratAdapter(getBaseContext(), dataList);
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void fetchDataFromAPI() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getsurat("all");
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
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
                                Surat surat = new Surat(
                                        dataObject.getString("id"),
                                        dataObject.getString("image"),
                                        dataObject.getString("nama_surat")
                                );

                                dataList.add(surat);
                                suratAdapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(ListJenisSuratActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
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