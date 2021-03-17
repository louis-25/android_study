package com.example.custom_listview;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class MyItem {

    private Uri icon;
    private String name;
    private String contents;

    public Uri getIcon() {
        return icon;
    }

    public void setIcon(Uri icon) {
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

}
