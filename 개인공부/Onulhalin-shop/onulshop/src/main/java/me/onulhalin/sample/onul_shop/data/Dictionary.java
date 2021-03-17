package me.onulhalin.sample.onul_shop.data;

public class Dictionary {

    private String id;
    private String mtest;
    private String mname;
    private String Imgname;
    private String mdraft;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgname() {
        return Imgname;
    }

    public void setImgname(String imgname) {
        this.Imgname = imgname;
    }

    public String getEnglish() {
        return mtest;
    }

    public void setEnglish(String english) {
        mtest = english;
    }

    public String getMdraft() {
        return mdraft;
    }

    public void setMdraft(String draft) {
        mdraft = draft;
    }


    public String getKorean() {
        return mname;
    }

    public void setKorean(String name) {
        mname = name;
    }

    public Dictionary(String id, String test, String name , String imgname , String draft) {
        this.id = id;
        mtest = test;
        mname = name;
        Imgname= imgname;
        mdraft = draft;
    }
}