package com.example.gcodepanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<MyHolder> implements SavedListener {

    private SharedPreferences.Editor editor;
    private List<DataPropil> dataPropils;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    ContentAdapter(Context context, String myMode) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(myMode, Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.contains("jsonlist")) {
            String jsonlist = preferences.getString("jsonlist", "");
            if (!jsonlist.equals("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DataPropil>>(){}.getType();
                dataPropils = gson.fromJson(jsonlist, type);
            } else {
                dataPropils = new ArrayList<>();
            }
        } else {
            dataPropils = new ArrayList<>();
        }
    }

    void addContent() {
        DataPropil dataPropil = new DataPropil();
        dataPropils.add(dataPropil);
        this.notifyItemInserted(getItemCount() - 1);
        saveData();
    }

    void clearContent() {
        int sz = dataPropils.size();
        dataPropils.clear();
        this.notifyItemRangeRemoved(0, sz);
        saveData();
    }

    private void saveData() {
        String jsnStr = new Gson().toJson(dataPropils);
        editor.putString("jsonlist", jsnStr);
        editor.apply();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TextView size = holder.size;
        size.setText(String.valueOf(dataPropils.get(position).getSize()));
        size.setOnClickListener(new FieldChanger(context, dataPropils,this, this, position));
        TextView nums = holder.nums;
        nums.setText(String.valueOf(dataPropils.get(position).getNums()));
        nums.setOnClickListener(new FieldChanger(context, dataPropils,this,this, position));
        ImageButton del = holder.del;
        del.setOnClickListener(new FieldChanger(context, dataPropils, this, this, position));
    }

    @Override
    public int getItemCount() {
        return dataPropils.size();
    }

    @Override
    public void onSave() {
        saveData();
    }
}
