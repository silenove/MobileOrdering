package com.example.silenove.menusystem;

import android.widget.Spinner;

/**
 * Created by silenove on 2015/12/12.
 */
public class SpinnerInfo {
    private int Image;
    private String tab;

    public SpinnerInfo(int Image,String tab){
        this.Image = Image;
        this.tab = tab;
    }

    public int getImage() {
        return Image;
    }

    public String getTab() {
        return tab;
    }
}
