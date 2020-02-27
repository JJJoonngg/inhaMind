package com.example.inhamind.EmailSend;

import android.content.Context;

public class MailSend {
    private String email;
    private Context context;
    public String authCode;

    public MailSend(Context context, String email, String authCode) {
        this.context = context;
        this.email = email;
        this.authCode = authCode;
    }

    public void sendMail() {
        JavaMailAPI javaMailAPI = new JavaMailAPI(context, email, authCode);
        javaMailAPI.execute();
    }
}