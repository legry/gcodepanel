package com.example.gcodepanel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityTableSetts extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablesetts);
        RecyclerView recyclerView = findViewById(R.id.tblrecycler);
        TableRecyclerAdapter tbl = new TableRecyclerAdapter(this);
        recyclerView.setAdapter(tbl);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles (not needed for Cards/Lists!)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
