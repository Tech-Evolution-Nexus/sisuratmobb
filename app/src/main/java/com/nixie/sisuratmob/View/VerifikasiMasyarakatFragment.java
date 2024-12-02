package com.nixie.sisuratmob.View;

import android.annotation.SuppressLint;
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
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.Models.VericikasimasModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ListJenisSuratAdapter;
import com.nixie.sisuratmob.View.Adapter.VerivikasimasAdapter;

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
 * Use the {@link VerifikasiMasyarakatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifikasiMasyarakatFragment extends Fragment {
    private RecyclerView recyclerView;
    private VerivikasimasAdapter verAdapter;
    private List<VericikasimasModel> dataList = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerifikasiMasyarakatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerifikasiMasyarakatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerifikasiMasyarakatFragment newInstance(String param1, String param2) {
        VerifikasiMasyarakatFragment fragment = new VerifikasiMasyarakatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_verifikasi_masyarakat, container, false);
        recyclerView = view.findViewById(R.id.recvermas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        verAdapter = new VerivikasimasAdapter(getContext(), dataList,this);
        recyclerView.setAdapter(verAdapter);
        fetchDataFromAPI();
        return view;
    }
    private void fetchDataFromAPI() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getVerifikasimas();
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
                                VericikasimasModel surat = new VericikasimasModel(
                                        dataObject.getString("nik"),
                                        dataObject.getString("nama_lengkap")
                                );

                                dataList.add(surat);
                                verAdapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void refreshFragment() {
        fetchDataFromAPI();
    }
}