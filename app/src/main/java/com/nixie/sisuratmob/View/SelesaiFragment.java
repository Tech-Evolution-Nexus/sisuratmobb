package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
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

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.StatusPengajuanAdapter;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelesaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelesaiFragment extends Fragment {
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

    public SelesaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelesaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelesaiFragment newInstance(String param1, String param2) {
        SelesaiFragment fragment = new SelesaiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        String nik = sharedPreferences.getString("nik", "");
        Log.d("TAG", nik);
        fetchData(nik,"selesai");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_selesai, container, false);
        recyclerViewRiwayatSurat = view.findViewById(R.id.recselesai);
        recyclerViewRiwayatSurat.setLayoutManager(new LinearLayoutManager(getContext()));
        statusPengajuanAdapter = new StatusPengajuanAdapter(getContext(),riwayatSuratList,this);
        recyclerViewRiwayatSurat.setAdapter(statusPengajuanAdapter);
        return view;
    }
    private void fetchData(String nik,String status) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getPengajuan(nik, status);
        String jsonResponse = "";
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        String responseBody = response.body().string();
                        Log.d("TAG", responseBody);
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
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}