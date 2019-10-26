package com.pingyuan.manager.bean;

public class User {

    private String userID;
    private String userName;
    private String loginID;
    private String loginPassword;
    private String userType;
    private String userProfession;
    private String userPicture;

    public User(String userID, String userName, String loginID, String loginPassword, String userType,
                String userProfession, String userPicture) {
        this.userID = userID;
        this.userName = userName;
        this.loginID = loginID;
        this.loginPassword = loginPassword;
        this.userType = userType;
        this.userProfession = userProfession;
        this.userPicture = userPicture;
    }

    public User() {
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

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}
