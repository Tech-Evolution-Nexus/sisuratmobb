package com.nixie.sisuratmob.View.PengajuanSurat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ApprovalPengajuanAdapter;

public class ApprovalPengajuanFragment extends Fragment {


    public ApprovalPengajuanFragment() {
        super(R.layout.fragment_approval_pengajuan);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi komponen dari layout
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Set adapter untuk ViewPager
        ApprovalPengajuanAdapter adapter = new ApprovalPengajuanAdapter(getActivity());
        viewPager.setAdapter(adapter);

        // Hubungkan ViewPager2 dengan TabLayout
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {

                    switch (position) {
                        case 0:
                            tab.setText("Surat Masuk");
                            break;
                        case 1:
                            tab.setText("Surat Selesai");
                            break;

                    }
                }
        ).attach();
    }
}