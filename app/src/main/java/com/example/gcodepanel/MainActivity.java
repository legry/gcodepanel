package com.example.gcodepanel;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int pos;
    private PropilFragment propilFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        ImageButton clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(this);
        FloatingActionButton sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);
        propilFragment = new PropilFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.propil_container, propilFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                propilFragment.getPropil().addContent();
                break;
            case R.id.clearBtn:
                propilFragment.getPropil().clearContent();
                break;
            case R.id.sendBtn:

                break;
        }
    }
}
