package com.example.appease.ViewHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appease.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView app_name;
    public ImageView app_icon, lock_app;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        app_name = itemView.findViewById(R.id.App_name);
        app_icon = itemView.findViewById(R.id.App_icon);
        lock_app = itemView.findViewById(R.id.Lock_app);


    }
}
