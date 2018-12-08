package com.example.gcodepanel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FieldChanger extends FlChr implements TextView.OnClickListener {

    private RecyclerView.Adapter<MyHolder> adapter;
    private Context context;
    private List<DataPropil> dataPropils;
    private SavedListener savedListener;
    private EditText edittext;
    private View view;

    FieldChanger(Context context, List<DataPropil> dataPropils, SavedListener savedListener, RecyclerView.Adapter<MyHolder> adapter, int pos) {
        super(pos);
        this.adapter = adapter;
        this.context = context;
        this.dataPropils = dataPropils;
        this.savedListener = savedListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.del) {
            dataPropils.remove(getCurr_pos());
            setRem_pos(getCurr_pos());
            adapter.notifyItemRemoved(getCurr_pos());
            savedListener.onSave();
        } else {
            dialogCreator(view);
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private void dialogCreator(View view) {
        setView(view);
        edittext = new EditText(context);
        edittext.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Введите значение :");

        switch (view.getId()) {
            case R.id.size:
                alert.setTitle("Размер");
                edittext.setText(String.valueOf(dataPropils.get(getCurr_pos()).getSize()));
                break;
            case R.id.nums:
                alert.setTitle("Колличество");
                edittext.setText(String.valueOf(dataPropils.get(getCurr_pos()).getNums()));
                break;
        }

        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edittext.getText().toString();
                if (!text.equals("")) {
                    int value = Integer.parseInt(text);
                    switch (getView().getId()) {
                        case R.id.size:
                            dataPropils.get(getCurr_pos()).setSize(value);
                            break;
                        case R.id.nums:
                            dataPropils.get(getCurr_pos()).setNums(value);
                            break;
                    }
                    adapter.notifyItemChanged(getCurr_pos());
                    savedListener.onSave();
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
