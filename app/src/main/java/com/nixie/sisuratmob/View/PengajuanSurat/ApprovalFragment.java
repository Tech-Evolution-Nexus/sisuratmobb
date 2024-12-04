package com.nixie.sisuratmob.View.PengajuanSurat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.PengajuanSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ApprovalPengajuanItemAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApprovalFragment extends Fragment {


    private List<PengajuanSuratModel> pengajuanSuratList = new ArrayList<>();
    private ApprovalPengajuanItemAdapter statusPengajuanAdapter;
    private static String status = "pending";

    public ApprovalFragment(String status) {
        this.status = status;
    }

    public static ApprovalFragment newInstance(String param1, String param2) {
        ApprovalFragment fragment = new ApprovalFragment(status);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approval_pending, container, false);
        RecyclerView recyclerViewRiwayatSurat = view.findViewById(R.id.pengajuan_list);
        recyclerViewRiwayatSurat.setLayoutManager(new LinearLayoutManager(getContext()));

        statusPengajuanAdapter = new ApprovalPengajuanItemAdapter(getContext(), pengajuanSuratList, status, this);
        recyclerViewRiwayatSurat.setAdapter(statusPengajuanAdapter);
        return view;
    }


    private void fetchData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String nik = sharedPreferences.getString("nik", null);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getListPengajuan(nik, status);
        String jsonResponse = "";
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PengajuanSuratModel> suratList = response.body().getData().getDataListPengajuan();
                    if (suratList != null) {
                        pengajuanSuratList.clear();
                        pengajuanSuratList.addAll(suratList);
                        statusPengajuanAdapter.notifyDataSetChanged();  // Refresh RecyclerView with new data
                    } else {
                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void refresh() {
        pengajuanSuratList.clear();
        fetchData();
    }
}