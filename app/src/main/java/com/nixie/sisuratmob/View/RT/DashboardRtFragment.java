package com.nixie.sisuratmob.View.RT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nixie.sisuratmob.Adapter.Warga.BeritaAdapter;
import com.nixie.sisuratmob.Models.Warga.Berita;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.List;


public class DashboardRtFragment extends Fragment {

    private RecyclerView recyclerViewBerita;
    private BeritaAdapter beritaAdapter;
    private List<Berita> beritaList;

    public DashboardRtFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_rt, container, false);

        recyclerViewBerita = view.findViewById(R.id.ViewBerita);
        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Inisialisasi list berita
        beritaList = new ArrayList<>();
        beritaAdapter = new BeritaAdapter(getActivity(), beritaList);
        recyclerViewBerita.setAdapter(beritaAdapter);

        ambilDataBerita();

        return view;
        // Required empty public constructor
    }


    private void ambilDataBerita() {
        // Contoh data statis
        beritaList.add(new Berita("Pengembangan aplikasi ", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        // Tambahkan berita lain sesuai kebutuhan
        beritaAdapter.notifyDataSetChanged();
    }
}