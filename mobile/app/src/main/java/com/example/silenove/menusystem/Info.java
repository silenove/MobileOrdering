package com.example.silenove.menusystem;

/**
 * Created by silenove on 2015/12/3.
 */
public class Info {
    private int myImgid;
    private String myPrice;
    private String myInfo;

    public Info(int myImgid,  String myInfo,String myPrice){
        this.myImgid = myImgid;
        this.myPrice = myPrice;
        this.myInfo = myInfo;
    }

    public int getMyImgid() {
        return myImgid;
    }

    public String getMyPrice() {
        return myPrice;
    }

    public String getMyInfo() {
        return myInfo;
    }
}
