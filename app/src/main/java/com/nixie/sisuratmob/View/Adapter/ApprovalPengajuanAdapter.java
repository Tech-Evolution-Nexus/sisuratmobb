package com.nixie.sisuratmob.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nixie.sisuratmob.View.PengajuanSurat.ApprovalFragment;

import java.util.ArrayList;
import java.util.List;

public class ApprovalPengajuanAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

    public ApprovalPengajuanAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
            fragmentList.add(new ApprovalFragment("pending"));
        fragmentList.add(new ApprovalFragment("selesai"));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ApprovalFragment("pending");
            case 1:
                return new ApprovalFragment("selesai");
            default:
                return new ApprovalFragment("pending");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
     public Fragment getFragment(int position) {
        return fragmentList.get(position);
    }
}

