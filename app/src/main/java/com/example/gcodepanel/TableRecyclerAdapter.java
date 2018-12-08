package com.example.gcodepanel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TableRecyclerAdapter extends RecyclerView.Adapter<TableRecyclerAdapter.Holder> {

    private Context context;
    private MyApp myApp;

    TableRecyclerAdapter(Context context) {
        this.context = context;
        myApp = (MyApp) context.getApplicationContext();
    }

    private void saveData() {
        myApp.saveChngDatas();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsablesetts, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") final int position) {
        holder.d.setText(String.valueOf(myApp.myFields.datas.get(position).getD()));
        holder.f.setText(String.valueOf(myApp.myFields.datas.get(position).getF()));
        holder.f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreator(position);
            }
        });
    }

    private void dialogCreator(final int pos) {
        final EditText edittext = new EditText(context);
        edittext.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Введите значение :");
        alert.setTitle("Скорость пиления для толщины заготовки " + String.valueOf(myApp.myFields.datas.get(pos).getD()));
        edittext.setText(String.valueOf(myApp.myFields.datas.get(pos).getF()));
        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edittext.getText().toString();
                if (!text.equals("")) {
                    int value = Integer.parseInt(text);
                    myApp.myFields.datas.get(pos).setF(value);
                    notifyItemChanged(pos);
                    saveData();
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

    @Override
    public int getItemCount() {
        return myApp.myFields.datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView d, f;
        Holder(View itemView) {
            super(itemView);
            d = itemView.findViewById(R.id._d);
            f = itemView.findViewById(R.id._f);
        }
    }
}
