package com.example.gcodepanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gcodepanel.GCode.DataPropil;

public class ContentAdapter extends RecyclerView.Adapter<MyHolder> implements SavedListener {

    private Context context;
    private int clr;
    private MyApp myApp;
    private String myMode;

    @SuppressLint("CommitPrefEdits")
    ContentAdapter(Context context, String myMode) {
        this.context = context;
        this.myMode = myMode;
        switch (myMode) {
            case "popirechn":
                clr = Color.BLUE;
                break;
            case "prodoln":
                clr = Color.RED;
                break;
        }
        myApp = (MyApp) context.getApplicationContext();
        myApp.dataPropil(myMode);
    }

    void addContent() {
        DataPropil dataPropil = new DataPropil();
        myApp.dataPropil(myMode).add(dataPropil);
        this.notifyItemInserted(getItemCount() - 1);
        saveData();
    }

    void clearContent() {
        int sz = myApp.dataPropil(myMode).size();
        myApp.dataPropil(myMode).clear();
        this.notifyItemRangeRemoved(0, sz);
        saveData();
    }

    private void saveData() {
        myApp.saveChngDataPropils();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txtsize.setTextColor(clr);
        holder.txtnums.setTextColor(clr);
        TextView size = holder.size;
        size.setText(String.valueOf(myApp.dataPropil(myMode).get(position).getSize()));
        size.setOnClickListener(new FieldChanger(context, myApp.dataPropil(myMode),this, this, position));
        TextView nums = holder.nums;
        nums.setText(String.valueOf(myApp.dataPropil(myMode).get(position).getNums()));
        nums.setOnClickListener(new FieldChanger(context, myApp.dataPropil(myMode),this,this, position));
        ImageButton del = holder.del;
        del.setOnClickListener(new FieldChanger(context, myApp.dataPropil(myMode), this, this, position));
    }

    @Override
    public int getItemCount() {
        return myApp.dataPropil(myMode).size();
    }

    @Override
    public void onSave() {
        saveData();
    }
}
