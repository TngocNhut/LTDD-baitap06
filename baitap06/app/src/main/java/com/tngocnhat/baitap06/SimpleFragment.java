package com.tngocnhat.baitap06;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class SimpleFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_IMG = "arg_img";

    public static SimpleFragment newInstance(String text, int imageRes) {
        SimpleFragment f = new SimpleFragment();
        Bundle b = new Bundle();
        b.putString(ARG_TEXT, text);
        b.putInt(ARG_IMG, imageRes);
        f.setArguments(b);
        return f;
    }

    public SimpleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_simple, container, false);
        TextView tv = root.findViewById(R.id.tvFragment);
        ImageView iv = root.findViewById(R.id.ivFragment);

        if (getArguments() != null) {
            tv.setText(getArguments().getString(ARG_TEXT));
            int img = getArguments().getInt(ARG_IMG, R.drawable.ic_sample1);
            Glide.with(requireContext()).load(img).into(iv);
        }
        return root;
    }
}
