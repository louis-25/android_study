package me.onulhalin.sample.onul_shop.data;

public class Dictionary {

    private String id;
    private String mtest;
    private String mname;
    private String Imgname;

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

    public String getKorean() {
        return mname;
    }

    public void setKorean(String korean) {
        mname = korean;
    }

    public Dictionary(String id, String test, String name , String imgname) {
        this.id = id;
        mtest = test;
        mname = name;
        Imgname= imgname;
    }
}