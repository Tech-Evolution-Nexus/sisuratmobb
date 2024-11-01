package com.nixie.sisuratmob.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nixie.sisuratmob.View.SuratDiajukanFragment;
import com.nixie.sisuratmob.View.SuratDiprosesFragment;
import com.nixie.sisuratmob.View.SuratDitolakFragment;
import com.nixie.sisuratmob.View.SuratSelesaiFragment;


public class StatusSuratAdapter extends FragmentStateAdapter {

    public StatusSuratAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SuratDiajukanFragment(); // Fragment untuk "Surat Diajukan"
            case 1:
                return new SuratDiprosesFragment(); // Fragment untuk "Surat Diproses"
            case 2:
                return new SuratSelesaiFragment();   // Fragment untuk "Surat Selesai"
            case 3:
                return new SuratDitolakFragment();   // Fragment untuk "Surat Ditolak"
            default:
                return new SuratDiajukanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Ada 4 tab
    }
}
