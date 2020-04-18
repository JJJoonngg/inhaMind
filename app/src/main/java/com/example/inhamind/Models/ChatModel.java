package com.example.inhamind.Models;
import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {

    public Map<String,Boolean> users = new HashMap<>(); //채팅방의 유저들
    public Map<String, Comment> comments = new HashMap<>(); //채팅방 대화내용
    public Map<String, Boolean> posts = new HashMap<>();

    public static  class Comment {
        public static String documnetID = "documentID";
        public static String postID = "postID";
        public String uid;
        public String message;
        public Object timestamp;
    }
}
