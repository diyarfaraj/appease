package com.example.appease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appease.R;
import com.example.appease.ViewHolder.MyViewHolder;
import com.example.appease.model.MyItem;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder > {
    private Context ctx;
    private List<MyItem> apps;

    public MyAdapter(Context ctx, List<MyItem> apps) {
        this.ctx = ctx;
        this.apps = apps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.my_view, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.app_name.setText(apps.get(position).getName());
        holder.app_icon.setImageDrawable(apps.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}
