package com.example.gcodepanel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class SettsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settsfragm1, container, false);
        RadioGroup gradchng = view.findViewById(R.id.gradchng);
        RadioGroup gradkamen = view.findViewById(R.id.kamenchng);
        gradchng.setOnCheckedChangeListener(this);
        gradkamen.setOnCheckedChangeListener(this);
        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.gradchng:
                switch (checkedId) {
                    case R.id.poper:

                        break;
                    case R.id.prodol:

                        break;
                }
                break;
            case R.id.kamenchng:
                switch (checkedId) {
                    case R.id.granit:

                        break;
                    case R.id.dolomit:

                        break;
                }
                break;
        }


    }
}
