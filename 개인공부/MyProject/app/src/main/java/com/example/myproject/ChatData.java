package com.example.myproject;

public class ChatData {
    private String Message;
    private String User_Name;
    private String User_Uri;
    private String User_Id;

    public ChatData(){ }

    public ChatData(String message, String user_Name, String user_Id, String user_Uri){
        this.Message = message;
        this.User_Name = user_Name;
        this.User_Id = user_Id;
        this.User_Uri = user_Uri;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Uri() {
        return User_Uri;
    }

    public void setUser_Uri(String user_Uri) {
        User_Uri = user_Uri;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }
}

