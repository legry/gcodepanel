package com.example.gcodepanel.GCode;

import android.util.Log;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class GCodeComander {

    public static final String[] mykamens = {"dolomit", "granit"};
    public static final String[] mymodes = {"popirechn", "prodoln"};
    private String[] coords;
    private MyFields myFields;

    private long F_dzag() {
        UnivariateInterpolator interpolator = new SplineInterpolator();
        double[] x = new double[myFields.datas.size()];
        double[] y = new double[myFields.datas.size()];
        for (int i = 0; i < myFields.datas.size(); i++) {
            x[i] = myFields.datas.get(i).getD();
            y[i] = myFields.datas.get(i).getF();
        }
        UnivariateFunction function = interpolator.interpolate(x, y);
        return Math.round(function.value(myFields.dzag));
    }

    public GCodeComander(MyFields myFields) {
        this.myFields = myFields;
        HashMap<String, String[]> coordMap = new HashMap<>();
        coordMap.put(mymodes[0], new String[]{"Y", "X", "-", "90", ""});
        coordMap.put(mymodes[1], new String[]{"X", "Y", "", "0", ""});
        coords = coordMap.get(myFields.propil);
        try {
            Method method = this.getClass().getMethod(myFields.kamen + "_propil", int.class);
            String result = (String) method.invoke(this, 300);
            Log.i("gcode", result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String Head() {
        return "G0 G53 Z0\n" +
                "G90 G0 C" +
                coords[3] +
                "\nG64\n" +
                "M03\n" +
                "M07\n";
    }

    public String dolomit_propil(int size) {
        return
                move_axis_on_with_F("Z", 0, myFields.F) + "\n" +
                        move_axis_on_with_F(coords[0] + coords[2], myFields.lzag, F_dzag()) + "\n" +
                        move_axis_on("Z", myFields.dzag + 20) + "\n" +
                        move_two_axis__ons(coords[0], coords[1], size + myFields.D_disca) + "\n";
    }

    private int cnt = 0;

    public String granit_propil(int size) {
        StringBuilder gs = new StringBuilder();
        float numpropil = (float) myFields.dzag/myFields.stepZ;
        int dzag_with_step = myFields.dzag;
        int cn1;
        for (cn1 = cnt; cn1 < (Math.floor(numpropil) + cnt); cn1++) {
            dzag_with_step -= myFields.stepZ;
            gs.append(one_granit_propil(cn1, dzag_with_step));
        }
        if (cn1 < numpropil) {
            gs.append(one_granit_propil(cn1, 0));
        }
        cnt = cn1 % 2 == 0 ? 0 : 1;
        gs.append(move_axis_on("Z", myFields.dzag + 20)).append("\n")
                .append(move_axis_on(coords[1], size + myFields.D_disca)).append("\n");

        return gs.toString();

    }

    private String one_granit_propil(int cn1, int coord) {
        String sign = ((cn1 % 2 == 0) ? coords[2] : coords[4]);
        int propcoord = ((cn1 % 2 == 0)) ? myFields.lzag : 0;
        return move_axis_on_with_F("Z", coord, myFields.F) + "\n" +
                move_axis_on_with_F(coords[0] + sign, propcoord, 10000) + "\n";
    }

    private String move_axis_on_with_F(String axis, int on, long F) {
        return "G01 " + axis + on + " F" + F;
    }

    private String move_axis_on(String axis, int on) {
        return "G90 G0 " + axis + on;
    }

    private String move_two_axis__ons(String ax1, String ax2, int on2) {
        return move_axis_on(ax1, 0) + " " + ax2 + on2;
    }
}
