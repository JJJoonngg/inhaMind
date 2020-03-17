package com.example.inhamind.Models;

public class UserModel {

    public String userName;
    public String profileImageUrl;
    public String uid;
    public String documnetID = "documentID";

    public UserModel() {

    }

    public UserModel(String userName, String profileImageUrl, String documnetID) {
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.documnetID = documnetID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDocumnetID() {
        return documnetID;
    }

    public void setDocumnetID(String documnetID) {
        this.documnetID = documnetID;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "documentId='" + documnetID + '\'' +
                ", name='" + userName + '\'' +
                ", profileUrl='" + profileImageUrl + '\'' +
                '}';
    }
}

