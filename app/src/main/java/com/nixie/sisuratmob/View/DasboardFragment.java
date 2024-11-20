package com.nixie.sisuratmob.View;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.JsuratdashAdapter;
import com.nixie.sisuratmob.View.Adapter.ListJenisSuratAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DasboardFragment extends Fragment {
    private ConstraintLayout Berita,Surat;
    private RecyclerView recyclerViewBerita,recyclerViewsurdash;
    private BeritaAdapter beritaAdapter;
    private JsuratdashAdapter jsuratAdapter;
    private List<Berita> dberitaList = new ArrayList<>();
    private List<Surat> dataList = new ArrayList<>();
    private List<Surat> filteredList = new ArrayList<>();

    private MaterialAutoCompleteTextView etSearch;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dasboard, container, false);
        Surat = view.findViewById(R.id.wrapjsur);
        Berita = view.findViewById(R.id.wrapbrita);
        recyclerViewBerita = view.findViewById(R.id.recyclerViewBerita);
        recyclerViewsurdash = view.findViewById(R.id.recjsurdash);
        etSearch = view.findViewById(R.id.carijsurdash);


        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewsurdash.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dberitaList = new ArrayList<>();
        dataList = new ArrayList<>();

        jsuratAdapter = new JsuratdashAdapter( getContext(),dataList);
        beritaAdapter = new BeritaAdapter(getContext(), dberitaList);



        recyclerViewsurdash.setAdapter(jsuratAdapter);
        recyclerViewsurdash.setHasFixedSize(true);

        recyclerViewBerita.setAdapter(beritaAdapter);
        recyclerViewBerita.setHasFixedSize(true);



        fetchdata();


        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
        etSearch.setAdapter(autoCompleteAdapter);

        etSearch.setOnItemClickListener((parent, v, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Surat selectedSurat = filteredList.get(position);
            Intent intent = new Intent(getActivity(), ListKeluargaActivity.class);
            intent.putExtra("id_surat", selectedSurat.getId());
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                filteredList.clear();
                for (Surat surat : dataList) {
                    if (surat.getNama_surat().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(surat);
                    }
                }
                jsuratAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

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
                Intent intent = new Intent(getActivity(),ListJenisSuratActivity.class);
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
        Call<ResponModel> call = apiService.getsurat();
        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                        List<Surat> suratList = response.body().getData().getDatasurat();

                    if (suratList != null) {
                        dataList.clear();
                        filteredList.clear();
                        dataList.addAll(suratList);
                        filteredList.addAll(suratList);
                        jsuratAdapter.notifyDataSetChanged();
                        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getSuratTitles());
                        etSearch.setAdapter(autoCompleteAdapter);
                        etSearch.getText().clear();
                        autoCompleteAdapter.getFilter().filter("");
                    } else {
                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponModel> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

            }
        });

        Call<ResponModel> call2 = apiService.getberita();
        call2.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponModel> call, @NonNull Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Berita> beritaList = response.body().getData().getDataberita();
                    if (beritaList != null) {
                        dberitaList.clear();
                        dberitaList.addAll(beritaList);
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
    private List<String> getSuratTitles() {
        List<String> titles = new ArrayList<>();
        for (Surat surat : dataList) {
            titles.add(surat.getNama_surat());  // Adjust this depending on your Surat model structure
        }
        return titles;
    }
}