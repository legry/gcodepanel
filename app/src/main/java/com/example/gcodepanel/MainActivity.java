package com.example.gcodepanel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPagerAdapter adapter;
    private int pos;
    private TabLayout tabLayout;
    private int[] myicons = {R.drawable.poper, R.drawable.prodol};
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (MyApp) getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tablayout", "tabSelected");
                pos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("tablayout", "tabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("tablayout", "tabReselected");
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("viewpager", "pageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                //Log.i("viewpager", "pageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.i("viewpager", "pageScrollStateChanged");
            }
        });

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(myicons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(myicons[1]);

        ImageButton addBtn = findViewById(R.id.sendBtn);
        addBtn.setOnClickListener(this);
        ImageButton clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(this);
        ImageButton settsbtn = findViewById(R.id.settsbtn);
        settsbtn.setOnClickListener(this);
        FloatingActionButton sendBtn = findViewById(R.id.addBtn);
        sendBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("activity", "destroy");
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PropilFragment(), "Продольный");
        adapter.addFragment(new PropilFragment(), "Поперечный");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                ((PropilFragment) adapter.getItem(pos)).getPropil().addContent();
                break;
            case R.id.clearBtn:
                ((PropilFragment) adapter.getItem(pos)).getPropil().clearContent();
                break;
            case R.id .settsbtn:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.sendBtn:
                Gson gson = new Gson();
                Log.i("myApp", gson.toJson(myApp.myFields));
                break;
        }
    }
}
