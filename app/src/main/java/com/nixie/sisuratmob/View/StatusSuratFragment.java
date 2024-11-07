package com.nixie.sisuratmob.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ViewPagerStatusAdapter;

public class StatusSuratFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    public StatusSuratFragment() {
        super(R.layout.fragment_status_surat);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi komponen dari layout
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Set adapter untuk ViewPager
        ViewPagerStatusAdapter adapter = new ViewPagerStatusAdapter(getActivity());
        viewPager.setAdapter(adapter);

        // Hubungkan ViewPager2 dengan TabLayout
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
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
                }
        ).attach();
    }
}