package com.tngocnhat.baitap06;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import me.relex.circleindicator.CircleIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipperMain;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private final Handler pagerHandler = new Handler();
    private Runnable pagerRunnable;
    private int[] flipperDrawables = {R.drawable.ic_sample1, R.drawable.ic_sample2, R.drawable.ic_sample3};

    private RecyclerView rcIcon;
    private IconAdapter iconAdapter;
    private final List<IconModel> iconList = new ArrayList<>();

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewFlipper
        viewFlipperMain = findViewById(R.id.viewFlipperMain);
        setupViewFlipper();

        // ViewPager + CircleIndicator
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.indicator);
        setupViewPagerWithIndicator();

        // RecyclerView horizontal + indicator + search
        rcIcon = findViewById(R.id.rcIcon);
        setupRecyclerView();

        SearchView searchView = findViewById(R.id.searchView);
        setupSearch(searchView);

        // ViewPager2 + TabLayout + Fragments
        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        setupViewPager2WithTabs();
    }

    private void setupViewFlipper() {
        if (viewFlipperMain == null) return;
        viewFlipperMain.removeAllViews();
        for (int drawable : flipperDrawables) {
            ImageView iv = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_viewflipper_image, null);
            iv.setImageResource(drawable);
            viewFlipperMain.addView(iv);
        }
        viewFlipperMain.setFlipInterval(3000);
        viewFlipperMain.setAutoStart(true);
    }

    private void setupViewPagerWithIndicator() {
        if (viewPager == null || circleIndicator == null) return;

        final List<Integer> images = new ArrayList<>();
        images.add(R.drawable.ic_sample1);
        images.add(R.drawable.ic_sample2);
        images.add(R.drawable.ic_sample3);

        SimplePagerAdapter pagerAdapter = new SimplePagerAdapter(this, images);
        viewPager.setAdapter(pagerAdapter);
        try {
            circleIndicator.setViewPager(viewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use WeakReference runnable to avoid leak
        final WeakReference<ViewPager> vpRef = new WeakReference<>(viewPager);
        pagerRunnable = new Runnable() {
            @Override
            public void run() {
                ViewPager vp = vpRef.get();
                if (vp == null) return;
                int count = vp.getAdapter() != null ? vp.getAdapter().getCount() : 0;
                if (count == 0) return;
                int next = (vp.getCurrentItem() + 1) % count;
                vp.setCurrentItem(next, true);
                pagerHandler.postDelayed(this, 3000);
            }
        };
        pagerHandler.postDelayed(pagerRunnable, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pagerRunnable != null) pagerHandler.postDelayed(pagerRunnable, 3000);
        if (viewFlipperMain != null) viewFlipperMain.startFlipping();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pagerHandler.removeCallbacksAndMessages(null);
        if (viewFlipperMain != null) viewFlipperMain.stopFlipping();
    }

    private void setupRecyclerView() {
        if (rcIcon == null) return;
        iconList.clear();
        iconList.add(new IconModel("Alpha", R.drawable.ic_sample1));
        iconList.add(new IconModel("Beta", R.drawable.ic_sample2));
        iconList.add(new IconModel("Gamma", R.drawable.ic_sample3));
        iconList.add(new IconModel("Delta", R.drawable.ic_sample1));
        iconList.add(new IconModel("Epsilon", R.drawable.ic_sample2));

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(lm);
        iconAdapter = new IconAdapter(new ArrayList<>(iconList));
        rcIcon.setAdapter(iconAdapter);

        LinePagerIndicatorDecoration decoration = new LinePagerIndicatorDecoration(this);
        rcIcon.addItemDecoration(decoration);
    }

    private void setupSearch(SearchView sv) {
        if (sv == null) return;
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (iconAdapter != null) iconAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (iconAdapter != null) iconAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setupViewPager2WithTabs() {
        if (viewPager2 == null || tabLayout == null) return;
        ViewPager2Adapter adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(adapter);

        // Use our SimpleTabMediator to avoid dependency on Kotlin class
        SimpleTabMediator mediator = new SimpleTabMediator(tabLayout, viewPager2, new SimpleTabMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) tab.setText(getString(R.string.tab_one));
                else if (position == 1) tab.setText(getString(R.string.tab_two));
                else tab.setText(getString(R.string.tab_three));
            }
        });
        mediator.attach();
    }
}
