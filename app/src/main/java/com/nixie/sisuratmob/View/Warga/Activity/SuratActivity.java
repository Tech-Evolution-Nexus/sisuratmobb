package com.nixie.sisuratmob.View.Warga.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nixie.sisuratmob.Adapter.Warga.SuratItemAdapter;
import com.nixie.sisuratmob.Models.Warga.Surat;
import com.nixie.sisuratmob.R;
import java.util.ArrayList;
import java.util.List;

public class SuratActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SuratItemAdapter suratAdapter;
    private List<Surat> listSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat);

        recyclerView = findViewById(R.id.SuratRcyl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listSurat = new ArrayList<>();
        listSurat.add(new Surat("Surat Keterangan", R.drawable.disabilitas));
        listSurat.add(new Surat("Surat Pengantar", R.drawable.deadt));
        // Tambahkan data surat lainnya

        suratAdapter = new SuratItemAdapter(this, listSurat);
        recyclerView.setAdapter(suratAdapter);
    }
}