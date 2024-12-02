package com.nixie.sisuratmob.View.PengajuanSurat;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.Models.DetailHistoriModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.PopupAdapter;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailApprovalActivity extends AppCompatActivity {
    private TextView titlejsurat, title_page, dateText, status, nama_lengkap, nik, alamat;
    private TextInputEditText detNamaLengkap, detNoKk, detKkTgl, detNik, detAlamat, detRt, detRw, detKodePos, detKelurahan, detKecamatan, detKabupaten, detProvinsi, detKeterangan;
    private RecyclerView recyclerViewLampiran;
    private PopupAdapter popupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_approval);
//        titlejsurat = findViewById(R.id.titlejsurat);
        alamat = findViewById(R.id.alamat);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        nik = findViewById(R.id.nik);
        title_page = findViewById(R.id.title_page);
        dateText = findViewById(R.id.datejsurat);
        recyclerViewLampiran = findViewById(R.id.recyclerViewpopup);
        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(this));


        String title = getIntent().getExtras().getString("title");
        String date = getIntent().getExtras().getString("date");
        String status = getIntent().getExtras().getString("status");
        int ipengajuan = getIntent().getExtras().getInt("idpengajuan");
        fetchDataFromApi(ipengajuan);
        title_page.setText(title);
        dateText.setText(Helpers.formatTanggal(date));


        View dbatalView = findViewById(R.id.btnbatalkansurat);
        View dcetakView = findViewById(R.id.dcetak);
        View btn_terima = findViewById(R.id.btn_terima);

        if ("pending".equals(status)) {
            dbatalView.setVisibility(View.VISIBLE);
            btn_terima.setVisibility(View.VISIBLE);
        }

        if ("selesai".equals(status)) {
            dcetakView.setVisibility(View.VISIBLE);
        }
    }

    private void fetchDataFromApi(int ipengajuan) {
//        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
//        Call<ResponModel> call = apiService.getdetailhistory(ipengajuan);
//
//        call.enqueue(new Callback<ResponModel>() {
//            @Override
//            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<BiodataModel> biodataList = response.body().getData().getBiodata();
//                    List<DetailHistoriModel> lampiranList = response.body().getData().getDatahistori();
//
//                    if (!biodataList.isEmpty()) {
//                        BiodataModel biodata = biodataList.get(0);
//                        nama_lengkap.setText(biodata.getNamaLengkap());
//                        alamat.setText(biodata.getAlamat());
//                        nik.setText(biodata.getNik());
//                    } else {
//                        Toast.makeText(getBaseContext(), "Data biodata kosong", Toast.LENGTH_SHORT).show();
//                    }
//
//                    popupAdapter = new PopupAdapter(lampiranList);
//                    recyclerViewLampiran.setAdapter(popupAdapter);
//                    recyclerViewLampiran.setNestedScrollingEnabled(false);
//                } else {
//                    Toast.makeText(getBaseContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponModel> call, Throwable t) {
//                Toast.makeText(getBaseContext(), "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void downloadPDF(Context context, String url, String title, int ipengajuan) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            Uri uri = Uri.parse(url);

            // Tentukan subdirektori
            String folderName = "Surat Badean";
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName);

            // Buat folder jika belum ada
            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    Toast.makeText(context, "Gagal membuat folder untuk unduhan.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Tentukan jalur file di subdirektori
            File file = new File(directory, title + "(" + ipengajuan + ").pdf");

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle("SURAT PDF");
            request.setDescription("File sedang diunduh...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            // Set URI tujuan dengan subdirektori
            request.setDestinationUri(Uri.fromFile(file));

            // Enqueue request ke DownloadManager
            downloadManager.enqueue(request);

            Toast.makeText(context, "Unduhan dimulai...", Toast.LENGTH_SHORT).show();
        }
    }

}