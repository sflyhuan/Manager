package com.pingyuan.manager.bean;

import java.util.Date;

public class Log {
    private String userID;
    private String userName;
    private String date;
    private String action;
    private String userPicture;

    public Log() {
    }

    public Log(String userID, String userName, String date, String action, String userPicture) {
        this.userID = userID;
        this.userName = userName;
        this.date = date;
        this.action = action;
        this.userPicture = userPicture;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}
