package com.example.inhamind.Board;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.R;

public class PostReadActivity extends AppCompatActivity {
    private String title, contents, studentID;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);
        intent = getIntent();
        title = intent.getStringExtra(DataName.titile);
        contents = intent.getStringExtra(DataName.contents);
        studentID = intent.getStringExtra(DataName.studentID);


    }
}
