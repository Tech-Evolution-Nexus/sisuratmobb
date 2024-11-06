package com.nixie.sisuratmob.View.Warga.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nixie.sisuratmob.Adapter.Warga.BeritaAdapter;
import com.nixie.sisuratmob.Models.Warga.Berita;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.tabs.TabLayout;

public class DasboardFragment extends Fragment {

    private RecyclerView recyclerViewBerita;
    private BeritaAdapter beritaAdapter;
    private List<Berita> beritaList;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dasboard, container, false);

        // Inisialisasi RecyclerView dan TabLayout
        recyclerViewBerita = view.findViewById(R.id.recyclerViewBerita);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Set RecyclerView untuk horizontal scroll
        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Inisialisasi list berita dan adapter
        beritaList = new ArrayList<>();
        beritaAdapter = new BeritaAdapter(getActivity(), beritaList);
        recyclerViewBerita.setAdapter(beritaAdapter);

        // Menambahkan data ke dalam RecyclerView
        ambilDataBerita();

        // Menambahkan tab ke TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Kategori 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Kategori 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Kategori 3"));

        // Listener untuk TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                List<Berita> filteredList = getFilteredData(position); // Filter data berdasarkan kategori
                beritaAdapter.updateData(filteredList); // Update adapter dengan data sesuai tab
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private void ambilDataBerita() {
        beritaList.add(new Berita("Pengembangan aplikasi ", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        beritaAdapter.notifyDataSetChanged();
    }

    // Fungsi untuk mendapatkan data sesuai kategori yang dipilih
    private List<Berita> getFilteredData(int categoryPosition) {
        List<Berita> filteredList = new ArrayList<>();
        // Filter data berita berdasarkan kategori (gunakan kondisi sesuai kebutuhan)
        if (categoryPosition == 0) {
            // Data untuk Kategori 1
            filteredList.add(new Berita("Pengembangan aplikasi", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        } else if (categoryPosition == 1) {
            // Data untuk Kategori 2
            filteredList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        }
        return filteredList;
    }
}

