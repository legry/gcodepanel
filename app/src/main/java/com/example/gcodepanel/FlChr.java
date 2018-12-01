package com.example.gcodepanel;

public class FlChr {
    private int curr_pos;

    FlChr(int curr_pos) {
        this.curr_pos = curr_pos;
    }

    public int getCurr_pos() {
        return curr_pos;
    }

    public void setRem_pos(int rem_pos) {
        if (curr_pos > rem_pos) {
            curr_pos--;
        }
    }
}
