package com.nixie.sisuratmob.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nixie.sisuratmob.Adapter.StatusSuratAdapter;
import com.nixie.sisuratmob.R;

public class StatusSuratFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    public StatusSuratFragment() {
        super(R.layout.fragment_status_surat); // Referensi ke layout fragment_status_surat.xml
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi komponen dari layout
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Set adapter untuk ViewPager
        StatusSuratAdapter adapter = new StatusSuratAdapter(this);
        viewPager.setAdapter(adapter);

        // Sinkronkan TabLayout dengan ViewPager menggunakan TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Surat Diajukan");
                    break;
                case 1:
                    tab.setText("Surat Diproses");
                    break;
                case 2:
                    tab.setText("Surat Selesai");
                    break;
                case 3:
                    tab.setText("Surat Ditolak");
                    break;
            }
        }).attach();
    }
}