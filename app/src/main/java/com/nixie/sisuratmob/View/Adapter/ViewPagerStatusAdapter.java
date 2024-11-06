package com.nixie.sisuratmob.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nixie.sisuratmob.View.DiajukanFragment;
import com.nixie.sisuratmob.View.DiprosesFragment;
import com.nixie.sisuratmob.View.DitolakFragment;
import com.nixie.sisuratmob.View.SelesaiFragment;

public class ViewPagerStatusAdapter extends FragmentStateAdapter {
    public ViewPagerStatusAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DiajukanFragment();
            case 1:
                return new DiprosesFragment();
            case 2:
                return new SelesaiFragment();
            case 3:
                return new DitolakFragment();
            default:
                return new DiajukanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // jumlah tab yang ingin ditampilkan
    }
}

