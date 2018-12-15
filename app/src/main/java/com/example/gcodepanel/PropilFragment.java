package com.example.gcodepanel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class PropilFragment extends Fragment implements View.OnClickListener {

    private ContentAdapter propil;
    private TextView lzag;
    private TextView dzag;

    public ContentAdapter getPropil() {
        return propil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.recycler, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        lzag = view.findViewById(R.id.l_zag);
        dzag = view.findViewById(R.id.d_zag);
        lzag.setText(String.valueOf(MyApp.myFields.lzag));
        dzag.setText(String.valueOf(MyApp.myFields.dzag));
        lzag.setOnClickListener(this);
        dzag.setOnClickListener(this);
        Bundle args = getArguments();
        String mymode = Objects.requireNonNull(args).getString("mymode");
        propil = new ContentAdapter(Objects.requireNonNull(getContext()), Objects.requireNonNull(mymode));
        recyclerView.setAdapter(propil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Set padding for Tiles (not needed for Cards/Lists!)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.l_zag: dialogCreator((R.string.lzag), view); break;
            case R.id.d_zag: dialogCreator((R.string.dzag), view); break;
        }
    }

    void updt_zag() {
        MyApp.myFields.lzag = Integer.parseInt(lzag.getText().toString());
        MyApp.myFields.dzag = Integer.parseInt(dzag.getText().toString());
    }

    private void dialogCreator(final int titleRes, final View view) {
        final EditText edittext = new EditText(getContext());
        edittext.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Введите значение :");
        alert.setTitle(getString(titleRes));
        edittext.setText(((TextView) view).getText());
        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edittext.getText().toString();
                if (!text.equals("")) {
                    ((TextView) view).setText(text);
                    switch (titleRes) {
                        case (R.string.lzag): MyApp.myFields.lzag = Integer.parseInt(text); break;
                        case (R.string.dzag): MyApp.myFields.dzag = Integer.parseInt(text); break;
                    }
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
    }
}
