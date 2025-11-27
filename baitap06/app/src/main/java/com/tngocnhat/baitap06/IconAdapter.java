package com.tngocnhat.baitap06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.VH> implements Filterable {

    private List<IconModel> data;
    private List<IconModel> dataAll; // copy for filter

    public IconAdapter(List<IconModel> list) {
        this.data = list;
        this.dataAll = new ArrayList<>(list);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_promotion, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        IconModel m = data.get(position);
        holder.tvTitle.setText(m.getTitle());
        Glide.with(holder.itemView.getContext()).load(m.getDrawableRes()).into(holder.img);
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle;
        VH(View v) {
            super(v);
            img = v.findViewById(R.id.imgIcon);
            tvTitle = v.findViewById(R.id.tvTitle);
        }
    }

    // Filter implementation
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String q = constraint == null ? "" : constraint.toString().trim().toLowerCase();
                List<IconModel> filtered = new ArrayList<>();
                if (q.isEmpty()) {
                    filtered.addAll(dataAll);
                } else {
                    for (IconModel item : dataAll) {
                        if (item.getTitle().toLowerCase().contains(q)) {
                            filtered.add(item);
                        }
                    }
                }
                FilterResults fr = new FilterResults();
                fr.values = filtered;
                return fr;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data.clear();
                data.addAll((List<IconModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
