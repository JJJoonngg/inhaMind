package com.example.inhamind.Board;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.R;

public class PostReadActivity extends AppCompatActivity {

    private TextView postTitle, postContents, postStudentID;
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

        postTitle = findViewById(R.id.post_title);
        postContents = findViewById(R.id.post_contents);

        postTitle.setText(title);
        postContents.setText(contents);

    }
}
