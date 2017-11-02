package com.example.silenove.menusystem;

import java.io.Serializable;

/**
 * Created by silenove on 2015/12/14.
 */
public class SelectedInfo implements Serializable{    //自定义对象，实现activity之间的信息传输
    private int Image;
    private String info;
    private String number;

    public SelectedInfo(int Image,String info,String amount){
        this.Image = Image;
        this.info = info;
        this.number = amount;
    }

    public int getImage() {
        return Image;
    }

    public String getInfo() {
        return info;
    }

    public String getNumber() {
        return number;
    }

    public void setAmount(String amount) {
        this.number = amount;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setImage(int image) {
        Image = image;
    }
}
