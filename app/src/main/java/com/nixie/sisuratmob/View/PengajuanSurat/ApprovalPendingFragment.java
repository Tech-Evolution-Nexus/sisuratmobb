package com.nixie.sisuratmob.View.PengajuanSurat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.PengajuanSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ApprovalPengajuanAdapter;
import com.nixie.sisuratmob.View.Adapter.ApprovalPengajuanItemAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApprovalPendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApprovalPendingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<PengajuanSuratModel> pengajuanSuratList = new ArrayList<>();
private ApprovalPengajuanItemAdapter statusPengajuanAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApprovalPendingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApprovalRtPendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApprovalPendingFragment newInstance(String param1, String param2) {
        ApprovalPendingFragment fragment = new ApprovalPendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData("0123456789012345");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view =  inflater.inflate(R.layout.fragment_approval_pending, container, false);
        RecyclerView recyclerViewRiwayatSurat = view.findViewById(R.id.pengajuan_list);
        recyclerViewRiwayatSurat.setLayoutManager(new LinearLayoutManager(getContext()));

        statusPengajuanAdapter = new ApprovalPengajuanItemAdapter(getContext(),pengajuanSuratList,"pending");
        recyclerViewRiwayatSurat.setAdapter(statusPengajuanAdapter);
        return view;
    }


    private void fetchData(String nik) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        String status = "pending";
        Call<ResponModel> call = apiService.getListPengajuan(nik,status);
        String jsonResponse = "";
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                    Log.d("Response Api",new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    List<PengajuanSuratModel> suratList = response.body().getData().getDataListPengajuan();
                    if (suratList != null) {
                        pengajuanSuratList.clear();
                        pengajuanSuratList.addAll(suratList);
                        statusPengajuanAdapter.notifyDataSetChanged();
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
}