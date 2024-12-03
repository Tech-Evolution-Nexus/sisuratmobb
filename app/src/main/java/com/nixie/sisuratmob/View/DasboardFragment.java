package com.nixie.sisuratmob.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.View.Adapter.JsuratdashAdapter;

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

public class    DasboardFragment extends Fragment {
    private ConstraintLayout Berita, Surat;
    private RecyclerView recyclerViewBerita, recyclerViewsurdash;
    private BeritaAdapter beritaAdapter;
    private JsuratdashAdapter jsuratAdapter;
    private List<Berita> dberitaList = new ArrayList<>();
    private List<Surat> dataList = new ArrayList<>();
    private List<Surat> filteredList = new ArrayList<>();
    private List<Surat>  dataListsearch = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private MaterialAutoCompleteTextView etSearch;
    private TextView txt5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dasboard, container, false);
        Surat = view.findViewById(R.id.wrapjsur);
        Berita = view.findViewById(R.id.wrapbrita);
        recyclerViewBerita = view.findViewById(R.id.recyclerViewBerita);
        recyclerViewsurdash = view.findViewById(R.id.recjsurdash);
        etSearch = view.findViewById(R.id.carijsurdash);
        txt5 = view.findViewById(R.id.textView5);
        sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        String nama = sharedPreferences.getString("namalengkap", "");
        txt5.setText("Hallo "+nama);

        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewsurdash.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dberitaList = new ArrayList<>();
        dataList = new ArrayList<>();

        jsuratAdapter = new JsuratdashAdapter(getContext(), dataList);
        beritaAdapter = new BeritaAdapter(getContext(), dberitaList);


        recyclerViewsurdash.setAdapter(jsuratAdapter);
        recyclerViewsurdash.setHasFixedSize(true);

        recyclerViewBerita.setAdapter(beritaAdapter);
        recyclerViewBerita.setHasFixedSize(true);


        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
        etSearch.setAdapter(autoCompleteAdapter);
        fetchdata();

        etSearch.setOnItemClickListener((parent, v, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Surat selectedSurat = filteredList.get(position);
            Intent intent = new Intent(getActivity(), ListKeluargaActivity.class);
            intent.putExtra("id_surat", selectedSurat.getId());
            startActivity(intent);
        });

//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                filteredList.clear();
//                for (Surat surat : dataListsearch) {
//                    if (surat.getNama_surat().toLowerCase().contains(charSequence.toString().toLowerCase())) {
////                        filteredList.add(surat);
//                    }
//                }
//                jsuratAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });

//        btnsur.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ListKeluargaActivity.class);
//                startActivity(intent);
//            }
//        });
        Surat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListJenisSuratActivity.class);
                startActivity(intent);
            }
        });
        Berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getActivity(), BritaallActivity.class);
                startActivity(inten);
            }
        });


        return view;
    }

    private void fetchdata() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getsurat("s");
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
                                Surat surat = new Surat(
                                        dataObject.getString("id"),
                                        dataObject.getString("image"),
                                        dataObject.getString("nama_surat")
                                );

                                dataList.add(surat);
                                jsuratAdapter.notifyDataSetChanged();
//                                ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
//                                etSearch.setAdapter(autoCompleteAdapter);
//                                etSearch.getText().clear();
//                                autoCompleteAdapter.getFilter().filter("");
                            }
                        }else{
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
//                        List<Surat> suratList = response.body().getData().getDatasurat();
//
//                    if (suratList != null) {
//                        dataList.clear();
//                        filteredList.clear();
//                        dataList.addAll(suratList);
//                        filteredList.addAll(suratList);
//                        jsuratAdapter.notifyDataSetChanged();
//                        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
//                        etSearch.setAdapter(autoCompleteAdapter);
//                        etSearch.getText().clear();
//                        autoCompleteAdapter.getFilter().filter("");
//                    } else {
//                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

            }
        });

        Call<ResponseBody> call2 = apiService.getberita("t");
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");

                        if (st) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                Berita berita = new Berita(dataObject.getInt("id"),
                                        dataObject.getString("judul"),
                                        dataObject.getString("sub_judul"),
                                        dataObject.getString("deskripsi"),
                                        dataObject.getString("gambar"),
                                        dataObject.getString("created_at"));
                                dberitaList.add(berita);
                                beritaAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
        Call<ResponseBody> call3 = apiService.getsurat("all");
        call3.enqueue(new Callback<ResponseBody>() {
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
                                Surat surat = new Surat(
                                        dataObject.getString("id"),
                                        dataObject.getString("image"),
                                        dataObject.getString("nama_surat")
                                );
                                dataListsearch.add(surat);
                                filteredList.add(surat);
                                ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
                                etSearch.setAdapter(autoCompleteAdapter);
                                etSearch.getText().clear();
                                autoCompleteAdapter.getFilter().filter("");
                            }
                        }else{
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
//                        List<Surat> suratList = response.body().getData().getDatasurat();
//
//                    if (suratList != null) {
//                        dataList.clear();
//                        filteredList.clear();
//                        dataList.addAll(suratList);
//                        filteredList.addAll(suratList);
//                        jsuratAdapter.notifyDataSetChanged();
//                        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
//                        etSearch.setAdapter(autoCompleteAdapter);
//                        etSearch.getText().clear();
//                        autoCompleteAdapter.getFilter().filter("");
//                    } else {
//                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private List<String> getSuratTitles() {
        List<String> titles = new ArrayList<>();
        for (Surat surat : dataListsearch) {
            titles.add(surat.getNama_surat());
        }
        return titles;
    }
}