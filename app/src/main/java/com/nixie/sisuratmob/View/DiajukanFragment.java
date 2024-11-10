package com.nixie.sisuratmob.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.StatusPengajuanAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiajukanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiajukanFragment extends Fragment {
    private TextView jtext,dtext,ntext,natext,status;
    private RecyclerView recyclerViewRiwayatSurat;
    private StatusPengajuanAdapter statusPengajuanAdapter;
    private List<RiwayatSurat> riwayatSuratList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiajukanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiajukanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiajukanFragment newInstance(String param1, String param2) {
        DiajukanFragment fragment = new DiajukanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData("1232313212133212","di_terima_rt");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_diajukan, container, false);
        recyclerViewRiwayatSurat = view.findViewById(R.id.recdiajukan);
        recyclerViewRiwayatSurat.setLayoutManager(new LinearLayoutManager(getContext()));
        statusPengajuanAdapter = new StatusPengajuanAdapter(riwayatSuratList);
        recyclerViewRiwayatSurat.setAdapter(statusPengajuanAdapter);
        return view;
    }
    private void fetchData(String nik,String status) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getPengajuan(nik, status);
        String jsonResponse = ""; // Replace with API response
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RiwayatSurat> suratList = response.body().getDatariwayat();
                    if (suratList != null) {
                        riwayatSuratList.clear();
                        riwayatSuratList.addAll(suratList);
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

}