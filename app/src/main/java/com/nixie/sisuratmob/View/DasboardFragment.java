package com.nixie.sisuratmob.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nixie.sisuratmob.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.List;

public class DasboardFragment extends Fragment {

    private RecyclerView recyclerViewBerita;
    private BeritaAdapter beritaAdapter;
    private List<Berita> beritaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dasboard, container, false);

        // Inisialisasi RecyclerView
        recyclerViewBerita = view.findViewById(R.id.recyclerViewBerita);
        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inisialisasi list berita
        beritaList = new ArrayList<>();
        beritaAdapter = new BeritaAdapter(getActivity(), beritaList);
        recyclerViewBerita.setAdapter(beritaAdapter);

        // Panggil fungsi untuk mengambil data berita
        ambilDataBerita();

        return view;
    }

    private void ambilDataBerita() {
        // Contoh data statis
        beritaList.add(new Berita("Kelurahan badean", "melakukan pengembangan aplikasi E surat", "hahahahahahahahahahahah", R.drawable.veriv));
        beritaList.add(new Berita("Judul Berita 2", "Subjudul Berita 2", "Deskripsi singkat berita 2", R.drawable.login));
        // Tambahkan berita lain sesuai kebutuhan
        beritaAdapter.notifyDataSetChanged();
    }
}
