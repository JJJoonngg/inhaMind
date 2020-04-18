package com.example.inhamind.Models;

public class TosData {
    public String title;
    public String content;

    public TosData() {
    }

    public TosData(String tos_title, String content) {
        this.title = tos_title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tos_title) {
        this.title = tos_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
