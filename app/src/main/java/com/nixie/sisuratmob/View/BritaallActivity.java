package com.nixie.sisuratmob.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;
import com.nixie.sisuratmob.View.Adapter.JsuratdashAdapter;

import java.util.ArrayList;
import java.util.List;

public class BritaallActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBerita;
    private BeritaAdapter beritaAdapter;
    private List<Berita> beritaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_britaall);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewBerita = findViewById(R.id.recbritaall);
        recyclerViewBerita.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        beritaList = new ArrayList<>();
        beritaAdapter = new BeritaAdapter(getBaseContext(), beritaList);
        recyclerViewBerita.setAdapter(beritaAdapter);
        recyclerViewBerita.setHasFixedSize(true);
        ambilDataBerita();

    }
    private void ambilDataBerita() {
        // Contoh data statis
        beritaList.add(new Berita("Pengembangan aplikasi ", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        beritaList.add(new Berita("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
//        suratList.add(new Surat("Pengembangan aplikasi", "sda"));
//        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
//        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
//        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
//        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
//        suratList.add(new Surat("Pengembangan aplikasi2", "sda"));
        // Tambahkan berita lain sesuai kebutuhan
        beritaAdapter.notifyDataSetChanged();
//        jsuratAdapter.notifyDataSetChanged();
    }
}