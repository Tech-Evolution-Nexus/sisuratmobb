package com.nixie.sisuratmob.komponen;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
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
import com.nixie.sisuratmob.View.PengajuanSurat.ApprovalFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPopup extends DialogFragment {
    private TextView titlejsurat, title_page, dateText, status, nama_lengkap, nik, alamat;
    private TextInputEditText detNamaLengkap, detNoKk, detKkTgl, detNik, detAlamat, detRt, detRw, detKodePos, detKelurahan, detKecamatan, detKabupaten, detProvinsi, detKeterangan;
    private RecyclerView recyclerViewLampiran;
    private PopupAdapter popupAdapter;
    private List<DetailHistoriModel> lampiranList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout dialog_biodata
        View view = inflater.inflate(R.layout.dialog_biodata, container, false);
//        titlejsurat = view.findViewById(R.id.titlejsurat);
        alamat = view.findViewById(R.id.alamat);
        nama_lengkap = view.findViewById(R.id.nama_lengkap);
        nik = view.findViewById(R.id.nik);
        title_page = view.findViewById(R.id.title_page);
        dateText = view.findViewById(R.id.datejsurat);

        recyclerViewLampiran = view.findViewById(R.id.recyclerViewpopup);
        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String status = getArguments().getString("status");
            View dbatalView = view.findViewById(R.id.btnbatalkansurat);
            View dcetakView = view.findViewById(R.id.dcetak);
            View btn_terima = view.findViewById(R.id.btn_terima);

            if ("pending".equals(status)) {
                dbatalView.setVisibility(View.VISIBLE);
                btn_terima.setVisibility(View.VISIBLE);

            }


            String date = getArguments().getString("date");
            String nik = getArguments().getString("nik");
            int ipengajuan = getArguments().getInt("idpengajuan");

            fetchDataFromApi(ipengajuan);
            title_page.setText(title);
            dateText.setText(Helpers.formatTanggal(date));
            dbatalView.setOnClickListener(v -> {
                approvalPengajuan(nik, ipengajuan, "ditolak");
            });
            btn_terima.setOnClickListener(v -> {
                approvalPengajuan(nik, ipengajuan, "diterima");
            });
            dcetakView.setOnClickListener(v -> {
                String url = "http://192.168.1.7/SISURAT/api/surat-selesai/export/" + ipengajuan;
                downloadPDF(getContext(), url, title, ipengajuan);
            });
        }
        return view;
    }


    private void approvalPengajuan(String nik, int ipengajuan, String status) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        RequestBody statusApproval = RequestBody.create(MediaType.parse("text/plain"), status);
        Call<ResponseBody> call = apiService.approvalPengajuan(nik, ipengajuan, statusApproval);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = null;
                    try {
                        responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        dismiss();
                        if (getParentFragment() != null) {
                         ((ApprovalFragment)   getParentFragment()).refresh();
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("API Response", responseBody);

                } else {
                    // Menangani error dari respons
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int screenHeight = size.y;
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = (int) (screenHeight * 0.8);
            getDialog().getWindow().setLayout(width, height);
        }
    }

    private void fetchDataFromApi(int ipengajuan) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getdetailhistory(ipengajuan);

        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BiodataModel> biodataList = response.body().getData().getBiodata();
                    List<DetailHistoriModel> lampiranList = response.body().getData().getDatahistori();

                    if (!biodataList.isEmpty()) {
                        BiodataModel biodata = biodataList.get(0);
                        nama_lengkap.setText(biodata.getNamaLengkap());
                        alamat.setText(biodata.getAlamat());
                        nik.setText(biodata.getNik());
                    } else {
                        Toast.makeText(getContext(), "Data biodata kosong", Toast.LENGTH_SHORT).show();
                    }

                    popupAdapter = new PopupAdapter(lampiranList);
                    recyclerViewLampiran.setAdapter(popupAdapter);
                    recyclerViewLampiran.setNestedScrollingEnabled(false);
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
            request.setTitle(title);
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

