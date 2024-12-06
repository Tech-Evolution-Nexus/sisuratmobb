package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
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
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.SuratbatalkAdapter;

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

public class DibatalkanActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRiwayatSurat;
    private SuratbatalkAdapter statusPengajuanAdapter;
    private List<RiwayatSurat> riwayatSuratList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dibatalkan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs",MODE_PRIVATE);
        String nik = sharedPreferences.getString("nik", "");
        fetchData(nik,"dibatalkan");
        recyclerViewRiwayatSurat = findViewById(R.id.recsurbat);
        recyclerViewRiwayatSurat.setLayoutManager(new LinearLayoutManager(this));
        statusPengajuanAdapter = new SuratbatalkAdapter(this,riwayatSuratList,this);
        recyclerViewRiwayatSurat.setAdapter(statusPengajuanAdapter);

    }
    private void fetchData(String nik,String status) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getPengajuan(nik, status);
        String jsonResponse = ""; // Replace with API response
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if(st){
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                RiwayatSurat listkk = new RiwayatSurat(
                                        dataObject.getInt("id"),
                                        dataObject.getInt("id_surat"),
                                        dataObject.getString("nomor_surat"),
                                        dataObject.getString("no_pengantar_rt"),
                                        dataObject.getString("no_pengantar_rw"),
                                        dataObject.getString("status"),
                                        dataObject.getString("keterangan"),
                                        dataObject.getString("keterangan_ditolak"),
                                        dataObject.getString("nik"),
                                        dataObject.getString("kode_kelurahan"),
                                        dataObject.getString("created_at"),
                                        dataObject.getString("updated_at"),
                                        dataObject.getString("nama_surat"),
                                        dataObject.getString("image")
                                );
                                riwayatSuratList.add(listkk);
                                statusPengajuanAdapter.notifyDataSetChanged();
                            }
                        }else{
//                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
//                    List<RiwayatSurat> suratList = response.body().getData().getDatariwayat();
//                    if (suratList != null) {
//                        riwayatSuratList.clear();
//                        riwayatSuratList.addAll(suratList);
//                        statusPengajuanAdapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
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