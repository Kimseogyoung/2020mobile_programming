package com.example.midtermproject;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class MyItem implements Serializable {

    private int icon;
    private String name;
    private String contents;
    private int price;
    private int num=1;
    private boolean checked=true;

    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPrice() { return price; }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() { return num; }
    public void setNum(int num) {
        this.num = num;
    }

    public boolean Ischecked() { return checked; }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }


}