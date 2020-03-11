package com.example.inhamind.Board;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class MyPostReadActivity extends AppCompatActivity {

    private TextView postTitle, postContents, postStatus;
    private String title, contents, studentID, status;
    @ServerTimestamp
    private Timestamp timestamp;
    private Intent intent;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_read);

        intent = getIntent();
        post = intent.getParcelableExtra(DataName.data);

        title = post.getTitle();
        contents = post.getContents();
        studentID = post.getStudentID();
        status = post.getStatus();
        timestamp = post.getTimestamp();

        postTitle = findViewById(R.id.post_title);
        postContents = findViewById(R.id.post_contents);
        postStatus = findViewById(R.id.post_status);

        postTitle.setText(title);
        postContents.setText(contents);

        if (status.equals("true")) {
            postStatus.setText("완료");
            postStatus.setTextColor(Color.GREEN);
        } else if (status.equals("false")) {
            postStatus.setText("미완료");
            postStatus.setTextColor(Color.RED);
        } else {
            postStatus.setText("진행중");
            postStatus.setTextColor(Color.BLUE);
        }
    }
}
