package com.example.gcodepanel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.gcodepanel.GCode.DataPropil;
import com.example.gcodepanel.GCode.Datas;
import com.example.gcodepanel.GCode.GCodeBuilder;
import com.example.gcodepanel.GCode.MyFields;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApp extends Application {
    public static MyFields myFields;
    private SharedPreferences.Editor editorDatas;
    private SharedPreferences.Editor editorDataPropils;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs;

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregListener();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myFields = new MyFields();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                switch (s) {
                    case "ipaddress": myFields.host = sharedPreferences.getString(s, ""); break;
                    case "stepZ": myFields.stepZ = Integer.parseInt(sharedPreferences.getString(s, "0")); break;
                    case "D_disca": myFields.D_disca = Integer.parseInt(sharedPreferences.getString(s, "0")); break;
                    case "F": myFields.F = Integer.parseInt(sharedPreferences.getString(s, "0")); break;
                }
            }
        };

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        myFields.host = prefs.getString("ipaddress", "");
        myFields.stepZ = Integer.parseInt(prefs.getString("stepZ", "0"));
        myFields.D_disca = Integer.parseInt(prefs.getString("D_disca", "0"));
        myFields.F = Integer.parseInt(prefs.getString("F", "0"));
        regListner();
        createDatas();
        createDataPropils();
    }

    void regListner() {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    void unregListener() {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void createDataPropils() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("mymap", Context.MODE_PRIVATE);
        editorDataPropils = preferences.edit();
        if (preferences.contains("jsonmap")) {
            String jsonlist = preferences.getString("jsonmap", "");
            if (!jsonlist.equals("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, List<DataPropil>>>(){}.getType();
                myFields.dataPropils = gson.fromJson(jsonlist, type);
            } else {
                myFields.dataPropils = new HashMap<>();
                myFields.dataPropils.put(GCodeBuilder.mymodes[0], new ArrayList<DataPropil>());
                myFields.dataPropils.put(GCodeBuilder.mymodes[1], new ArrayList<DataPropil>());
            }
        } else {
            myFields.dataPropils = new HashMap<>();
            myFields.dataPropils.put(GCodeBuilder.mymodes[0], new ArrayList<DataPropil>());
            myFields.dataPropils.put(GCodeBuilder.mymodes[1], new ArrayList<DataPropil>());
        }
    }

    List<DataPropil> dataPropil(String myMode) {
        return myFields.dataPropils.get(myMode);
    }

    void saveChngDataPropils() {
        String jsnStr = new Gson().toJson(myFields.dataPropils);
        editorDataPropils.putString("jsonmap", jsnStr);
        editorDataPropils.apply();
    }

    private void createDatas() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("table_data", Context.MODE_PRIVATE);
        editorDatas = preferences.edit();
        if (preferences.contains("datastring")) {
            String jsonlist = preferences.getString("datastring", "");
            if (!jsonlist.equals("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Datas>>(){}.getType();
                myFields.datas = gson.fromJson(jsonlist, type);
            } else {
                createNewDatas();
            }
        } else {
            createNewDatas();
        }
    }

    void saveChngDatas() {
        String jsnStr = new Gson().toJson(myFields.datas);
        editorDatas.putString("datastring", jsnStr);
        editorDatas.apply();
    }

    private void createNewDatas() {
        myFields.datas = new ArrayList<>();
        for (int i = 2; i < 16; i++) {
            myFields.datas.add(new Datas(i*10));
        }
    }
}
