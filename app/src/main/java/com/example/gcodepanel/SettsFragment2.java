package com.example.gcodepanel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettsFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settsfragm2, container, false);
        EditText dzag = view.findViewById(R.id.dzag);
        EditText lzag = view.findViewById(R.id.lzag);
        return view;
    }
}
