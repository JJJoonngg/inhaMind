package com.example.inhamind.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Notice implements Parcelable {
    private String documentID;
    private String noticeID;
    private String name;
    private String title;
    private String contents;
    @ServerTimestamp
    private Timestamp timestamp;

    public Notice() {
    }

    public Notice(String documentID, String noticeID, String name, String title, String contents, Timestamp timestamp) {
        this.documentID = documentID;
        this.noticeID = noticeID;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    protected Notice(Parcel in) {
        documentID = in.readString();
        noticeID = in.readString();
        name = in.readString();
        title = in.readString();
        contents = in.readString();
        timestamp = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "documentID='" + documentID + '\'' +
                ", noticeID='" + noticeID + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentID);
        parcel.writeString(noticeID);
        parcel.writeString(name);
        parcel.writeString(title);
        parcel.writeString(contents);
        parcel.writeParcelable(timestamp, i);
    }
}
