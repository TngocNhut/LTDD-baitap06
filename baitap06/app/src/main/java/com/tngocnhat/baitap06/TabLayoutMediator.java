package com.tngocnhat.baitap06;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager2.widget.ViewPager2;

public class TabLayoutMediator {

    public interface TabConfigurationStrategy {
        void onConfigureTab(@NonNull TabLayout.Tab tab, int position);
    }

    private final TabLayout tabLayout;
    private final ViewPager2 viewPager;
    private final TabConfigurationStrategy strategy;

    public TabLayoutMediator(TabLayout tabLayout, ViewPager2 viewPager, TabConfigurationStrategy strategy) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.strategy = strategy;
    }

    public void attach() {
        int count = viewPager.getAdapter() != null ? viewPager.getAdapter().getItemCount() : 0;
        tabLayout.removeAllTabs();
        for (int i = 0; i < count; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            strategy.onConfigureTab(tab, i);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) { viewPager.setCurrentItem(tab.getPosition()); }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) { viewPager.setCurrentItem(tab.getPosition()); }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                if (position < tabLayout.getTabCount()) {
                    TabLayout.Tab tab = tabLayout.getTabAt(position);
                    if (tab != null) tab.select();
                }
            }
        });
    }
}
