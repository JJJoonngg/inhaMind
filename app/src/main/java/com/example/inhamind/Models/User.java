package com.example.inhamind.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String documentID;
    private String name;
    private String studentID;
    private String profileUrl;

    public User() {
    }

    public User(String documentID, String name, String studentID, String profileUrl) {
        this.documentID = documentID;
        this.name = name;
        this.studentID = studentID;
        this.profileUrl = profileUrl;
    }

    protected User(Parcel in) {
        documentID = in.readString();
        name = in.readString();
        studentID = in.readString();
        profileUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "documentID='" + documentID + '\'' +
                ", name='" + name + '\'' +
                ", studentID='" + studentID + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentID);
        parcel.writeString(name);
        parcel.writeString(studentID);
        parcel.writeString(profileUrl);
    }
}
