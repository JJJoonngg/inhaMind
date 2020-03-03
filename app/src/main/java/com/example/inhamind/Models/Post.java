package com.example.inhamind.Models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {
    private String documentID;
    private String title;
    private String contents;
    private String studentID;
    @ServerTimestamp
    private Date date;

    public Post() {

    }

    public Post(String documentID, String title, String contents, String studentID) {
        this.documentID = documentID;
        this.title = title;
        this.contents = contents;
        this.studentID = studentID;
    }

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

    public Date getDate() {
        return date;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentID='" + documentID + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", studentID='" + studentID + '\'' +
                ", date=" + date +
                '}';
    }
}
