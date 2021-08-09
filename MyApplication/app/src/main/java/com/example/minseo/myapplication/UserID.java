package com.example.minseo.myapplication;

import android.app.Application;

public class UserID extends Application {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    private String userPW;

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW){
        this.userPW = userPW;
    }
}
