package com.nixie.sisuratmob.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nixie.sisuratmob.View.PengajuanSurat.ApprovalDoneFragment;
import com.nixie.sisuratmob.View.PengajuanSurat.ApprovalPendingFragment;

public class ApprovalPengajuanAdapter extends FragmentStateAdapter {
    public ApprovalPengajuanAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ApprovalPendingFragment();
            case 1:
                return new ApprovalDoneFragment();
            default:
                return new ApprovalPendingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

