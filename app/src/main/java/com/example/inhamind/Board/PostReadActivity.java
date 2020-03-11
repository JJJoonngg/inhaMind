package com.example.inhamind.Board;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class PostReadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView postTitle, postContents, postStudentID, postStatus;
    private String title, contents, studentID, status;
    private Intent intent;
    private Post post;
    @ServerTimestamp
    private Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);

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
            postStatus.setTextColor(Color.BLUE);
        } else {
            postStatus.setText("미완료");
            postStatus.setTextColor(Color.RED);
        }


        findViewById(R.id.read_close_button).setOnClickListener(this);
        findViewById(R.id.read_chat_button).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_close_button:
                this.finish();
                break;

            case R.id.read_chat_button:
                Toast.makeText(this,"채팅 구현 예정", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
