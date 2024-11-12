package com.nixie.sisuratmob.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.LampiranAdapter;
import java.util.List;

public class FormPengajuanActivity extends AppCompatActivity {
    private TextInputEditText etNamaLengkap, etNoKk,etKkTgl, etNik, etAlamat, etRt, etRw, etKodePos, etKelurahan, etKecamatan, etKabupaten, etProvinsi;
    private RecyclerView recyclerViewLampiran;
    private LampiranAdapter lampiranAdapter;
    private List<LampiranSuratModel> lampiranList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pengajuan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNoKk = findViewById(R.id.etNoKk);
        etNik = findViewById(R.id.etNik);
        etAlamat = findViewById(R.id.etAlamat);
        etRt = findViewById(R.id.etRt);
        etRw = findViewById(R.id.etRw);
        etKodePos = findViewById(R.id.etKodePos);
        etKelurahan = findViewById(R.id.etKelurahan);
        etKecamatan = findViewById(R.id.etKecamatan);
        etKabupaten = findViewById(R.id.etKabupaten);
        etProvinsi = findViewById(R.id.etProvinsi);
        etKkTgl = findViewById(R.id.etKkTgl);
        recyclerViewLampiran = findViewById(R.id.recyclerViewlampiran);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(this));
        String idsurat = getIntent().getStringExtra("id_surat");
        String idnik = getIntent().getStringExtra("nik");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fetchDataFromApi(idnik,idsurat);


    }
    private void fetchDataFromApi(String nik,String idsur) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getDataForm(nik, Integer.parseInt(idsur));

        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BiodataModel> biodataList = response.body().getData().getBiodata();
                    List<LampiranSuratModel> lampiranList = response.body().getData().getDatalampiran();

                    if (!biodataList.isEmpty()) {
                        BiodataModel biodata = biodataList.get(0);
                        etNamaLengkap.setText(biodata.getNamaLengkap());
                        etNoKk.setText(biodata.getNoKk());
                        etNik.setText(biodata.getNik());
                        etAlamat.setText(biodata.getAlamat());
                        etKkTgl.setText(biodata.getKkTgl());
                        etRt.setText(String.valueOf(biodata.getRt()));
                        etRw.setText(String.valueOf(biodata.getRw()));
                        etKodePos.setText(String.valueOf(biodata.getKodePos()));
                        etKelurahan.setText(biodata.getKelurahan());
                        etKecamatan.setText(biodata.getKecamatan());
                        etKabupaten.setText(biodata.getKabupaten());
                        etProvinsi.setText(biodata.getProvinsi());
                    } else {
                        Toast.makeText(FormPengajuanActivity.this, "Data biodata kosong", Toast.LENGTH_SHORT).show();
                    }

                    // Setup RecyclerView untuk lampiran
                    lampiranAdapter = new LampiranAdapter(lampiranList);
                    recyclerViewLampiran.setAdapter(lampiranAdapter);
                    recyclerViewLampiran.setNestedScrollingEnabled(false);
                } else {
                    Toast.makeText(FormPengajuanActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                Toast.makeText(FormPengajuanActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
