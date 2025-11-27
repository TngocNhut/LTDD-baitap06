package com.tngocnhat.baitap06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class SimplePagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> images;

    public SimplePagerAdapter(Context ctx, List<Integer> images) {
        this.context = ctx;
        this.images = images;
    }

    @Override
    public int getCount() { return images.size(); }

    @Override
    public boolean isViewFromObject(View view, Object object) { return view == object; }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_viewflipper_image, container, false);
        Glide.with(context).load(images.get(position)).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
