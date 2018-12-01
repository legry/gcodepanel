package com.example.gcodepanel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

class MyHolder extends RecyclerView.ViewHolder {

    TextView size, nums, lengthPropila;
    ImageButton del;

    MyHolder(View itemView) {
        super(itemView);
        size = itemView.findViewById(R.id.size);
        nums = itemView.findViewById(R.id.nums);
        lengthPropila = itemView.findViewById(R.id.length);
        del = itemView.findViewById(R.id.del);
    }
}