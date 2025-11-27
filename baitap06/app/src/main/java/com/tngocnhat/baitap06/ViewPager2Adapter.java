package com.tngocnhat.baitap06;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return SimpleFragment.newInstance("Fragment A", R.drawable.ic_sample1);
        if (position == 1) return SimpleFragment.newInstance("Fragment B", R.drawable.ic_sample2);
        return SimpleFragment.newInstance("Fragment C", R.drawable.ic_sample3);
    }

    @Override
    public int getItemCount() { return 3; }
}
