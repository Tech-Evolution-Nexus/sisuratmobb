package com.nixie.sisuratmob.komponen;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
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
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.Models.DetailHistoriModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.LampiranAdapter;
import com.nixie.sisuratmob.View.Adapter.PopupAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPopup extends DialogFragment {
    private TextView titlejsurat,dateText,status;
    private TextInputEditText detNamaLengkap, detNoKk,detKkTgl, detNik, detAlamat, detRt, detRw, detKodePos, detKelurahan, detKecamatan, detKabupaten, detProvinsi,detKeterangan;
    private RecyclerView recyclerViewLampiran;
    private PopupAdapter popupAdapter;
    private List<DetailHistoriModel> lampiranList;
    private String nik, idSurat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout dialog_biodata
        View view =  inflater.inflate(R.layout.dialog_biodata, container, false);
        titlejsurat = view.findViewById(R.id.titlejsurat);
        dateText = view.findViewById(R.id.datejsurat);
        detNamaLengkap = view.findViewById(R.id.detNamaLengkap);
        detNoKk = view.findViewById(R.id.detNoKk);
        detNik = view.findViewById(R.id.detNik);
        detAlamat = view.findViewById(R.id.detAlamat);
        detRt = view.findViewById(R.id.detRt);
        detRw = view.findViewById(R.id.detRw);
        detKodePos = view.findViewById(R.id.detKodePos);
        detKelurahan = view.findViewById(R.id.detKelurahan);
        detKecamatan = view.findViewById(R.id.detKecamatan);
        detKabupaten = view.findViewById(R.id.detKabupaten);
        detProvinsi = view.findViewById(R.id.detProvinsi);
        detKkTgl = view.findViewById(R.id.detKkTgl);
        detKeterangan = view.findViewById(R.id.detketerangan);

        recyclerViewLampiran = view.findViewById(R.id.recyclerViewpopup);
        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String status = getArguments().getString("status");
            String date = getArguments().getString("date");
            String nik = getArguments().getString("nik");
            int ipengajuan = getArguments().getInt("idpengajuan");

            fetchDataFromApi(ipengajuan);
            titlejsurat.setText(title);
            dateText.setText(date);

        }
        return view;
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
                        detNamaLengkap.setText(biodata.getNamaLengkap());
                        detNoKk.setText(biodata.getNoKk());
                        detNik.setText(biodata.getNik());
                        detAlamat.setText(biodata.getAlamat());
                        detKkTgl.setText(biodata.getKkTgl());
                        detRt.setText(String.valueOf(biodata.getRt()));
                        detRw.setText(String.valueOf(biodata.getRw()));
                        detKodePos.setText(String.valueOf(biodata.getKodePos()));
                        detKelurahan.setText(biodata.getKelurahan());
                        detKecamatan.setText(biodata.getKecamatan());
                        detKabupaten.setText(biodata.getKabupaten());
                        detProvinsi.setText(biodata.getProvinsi());
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
}
