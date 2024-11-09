package com.nixie.sisuratmob.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.JsuratdashAdapter;

import java.util.ArrayList;
import java.util.List;

public class DasboardFragment extends Fragment {


    private TextView Berita;
    private TextView Surat;
    private RecyclerView recyclerViewBerita,recyclerViewsurdash;
    private BeritaAdapter beritaAdapter;
    private JsuratdashAdapter jsuratAdapter;
    private List<Berita> beritaList;
    private List<Surat> suratList;
    private LinearLayout btnsur;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dasboard, container, false);
        Surat = view.findViewById(R.id.textSurat);
        Berita = view.findViewById(R.id.textBerita);

        recyclerViewBerita = view.findViewById(R.id.recyclerViewBerita);
        recyclerViewBerita.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewsurdash = view.findViewById(R.id.recjsurdash);
        recyclerViewsurdash.setLayoutManager(new GridLayoutManager(getContext(), 2));

        beritaList = new ArrayList<>();
        suratList = new ArrayList<>();

        jsuratAdapter = new JsuratdashAdapter( getContext(),suratList);
        beritaAdapter = new BeritaAdapter(getContext(), beritaList);

        recyclerViewsurdash.setAdapter(jsuratAdapter);
        recyclerViewsurdash.setHasFixedSize(true);

        recyclerViewBerita.setAdapter(beritaAdapter);
        recyclerViewBerita.setHasFixedSize(true);

        ambilDataBerita();

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
                Intent inten = new Intent(getActivity(),DashboardActivity.class);
                startActivity(inten);
            }
        });


        return view;
    }
    private void ambilDataBerita() {
        // Contoh data statis
        beritaList.add(new Berita("Pengembangan aplikasi ", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        suratList.add(new Surat("Pengembangan aplikasi", "sda"));
        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        // Tambahkan berita lain sesuai kebutuhan
        beritaAdapter.notifyDataSetChanged();
        jsuratAdapter.notifyDataSetChanged();
    }
}
