package com.tngocnhat.baitap06;

public class IconModel {
    private String title;
    private int drawableRes;

    public IconModel(String title, int drawableRes) {
        this.title = title;
        this.drawableRes = drawableRes;
    }

    public String getTitle() { return title; }
    public int getDrawableRes() { return drawableRes; }
}
