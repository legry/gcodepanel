package com.example.gcodepanel.GCode;

import java.util.List;
import java.util.Map;

public class MyFields {
    public String kamen = GCodeBuilder.mykamens[0];
    public String propil = GCodeBuilder.mymodes[0];
    public int lzag = 0;
    public int dzag = 0;
    public String host;
    public int stepZ;
    public int D_disca;
    public int F;
    public Map<String, List<DataPropil>> dataPropils;
    public List<Datas> datas;
}
