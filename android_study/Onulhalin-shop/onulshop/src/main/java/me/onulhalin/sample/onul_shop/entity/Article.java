package me.onulhalin.sample.onul_shop.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Article implements Parcelable{
    private String title;
    private String content;
    private String id;
    private String imgRes;
    private String datetime;
    private String status;

    public Article(String title, String content,String datetime,String mid,String mstatus) {
        this.title = title;
        this.content = content;
        this.datetime = datetime;
        this.id = mid;
        this.status = mstatus;
    }



    protected Article(Parcel in) {
        title = in.readString();
        content = in.readString();
        imgRes = in.readString();
        id = in.readString();
        datetime = in.readString();
        status = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String mstatus) {
        this.status = mstatus;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setDatetime(String datetime){
        this.datetime=datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imgRes);
        dest.writeString(datetime);
        dest.writeString(id);
        dest.writeString(status);
    }
}
