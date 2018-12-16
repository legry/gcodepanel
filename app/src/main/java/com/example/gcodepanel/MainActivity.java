package com.example.gcodepanel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.gcodepanel.GCode.GCodeBuilder;

import java.util.Objects;

import static com.example.gcodepanel.GCode.GCodeBuilder.mykamens;
import static com.example.gcodepanel.GCode.GCodeBuilder.mymodes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1234;
    private ViewPagerAdapter adapter;
    private int pos;
    private int[] myicons = {R.drawable.poper, R.drawable.prodol};
    private String[] my_perms = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        BottomNavigationView kamenchng = findViewById(R.id.kamenchng);
        kamenchng.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dolomit:
                        MyApp.myFields.kamen = mykamens[0];
                        break;
                    case R.id.granit:
                        MyApp.myFields.kamen = mykamens[1];
                        break;
                }
                return true;
            }
        });
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pos = tab.getPosition();
                MyApp.myFields.propil = mymodes[pos];
                adapter.updt_zag(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PropilFragment(), "Продольный");
        adapter.addFragment(new PropilFragment(), "Поперечный");
        viewPager.setAdapter(adapter);
    }

    private void checkedPerms() {
        checkedPerms(my_perms[0]);
    }

    private void checkedPerms(String perm) {
        int permissionStatus = ContextCompat.checkSelfPermission(this, perm);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            if (perm.equals(my_perms[0])) checkedPerms(my_perms[1]);
            else new GCodeBuilder(MainActivity.this, MyApp.myFields).createFullGCode();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{perm}, REQUEST_CODE_PERMISSION_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (permissions[0].equals(my_perms[0])) checkedPerms(my_perms[1]);
                    else new GCodeBuilder(MainActivity.this, MyApp.myFields).createFullGCode();
                }
        }
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
                checkedPerms();
                break;
        }
    }
}
