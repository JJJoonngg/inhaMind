package com.example.inhamind.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Post implements Parcelable {
    private String documentID;
    private String title;
    private String contents;
    private String studentID;
    private String status;
    @ServerTimestamp
    private Timestamp timestamp;

    public Post() {

    }

    public Post(String documentID, String title, String contents, String studentID, String status, Timestamp timestamp) {
        this.documentID = documentID;
        this.title = title;
        this.contents = contents;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }

    protected Post(Parcel in) {
        documentID = in.readString();
        title = in.readString();
        contents = in.readString();
        studentID = in.readString();
        status = in.readString();
        timestamp = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentID='" + documentID + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", studentID='" + studentID + '\'' +
                ", status=" + status +
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
        parcel.writeString(title);
        parcel.writeString(contents);
        parcel.writeString(studentID);
        parcel.writeString(status);
        parcel.writeParcelable(timestamp, i);
    }
}
