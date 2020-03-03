package com.example.inhamind.Models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {
    private String documentID;
    private String title;
    private String contents;
    private String studentNumber;
    @ServerTimestamp
    private Date date;

    public Post() {

    }

    public Post(String documentID, String title, String contents, String studentNumber) {
        this.documentID = documentID;
        this.title = title;
        this.contents = contents;
        this.studentNumber = studentNumber;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentID='" + documentID + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", date=" + date +
                '}';
    }
}
