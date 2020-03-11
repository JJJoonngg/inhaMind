package com.example.inhamind.Board;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class MyPostReadActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText postTitle, postContents, postStatus;
    private String title, contents, studentID, status;
    @ServerTimestamp
    private Timestamp timestamp;
    private Intent intent;
    private Post post;
    private Button doneBtn, cancelBtn;

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
        doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(this);
        cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(this);

        postTitle.setText("제목 : " + title);
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

        findViewById(R.id.close_button).setOnClickListener(this);
        findViewById(R.id.option_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                this.finish();
                break;
            case R.id.option_button:
                PopupMenu popupMenu = new PopupMenu(MyPostReadActivity.this, view);
                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(listener);
                popupMenu.show();
                break;

            case R.id.cancel:
                postTitle.setText("제목 : " + title);
                postContents.setText(contents);
                doneBtn.setVisibility(View.INVISIBLE);
                doneBtn.setFocusable(false);
                doneBtn.setClickable(false);
                cancelBtn.setVisibility(View.INVISIBLE);
                cancelBtn.setFocusable(false);
                cancelBtn.setClickable(false);
                postTitle.setFocusable(false);
                postTitle.setClickable(false);
                postContents.setFocusable(false);
                postContents.setFocusable(false);
                break;
            case R.id.done:
                break;
        }
    }

    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.modify:
                    doneBtn.setVisibility(View.VISIBLE);
                    doneBtn.setClickable(true);
                    doneBtn.setFocusable(true);
                    cancelBtn.setVisibility(View.VISIBLE);
                    cancelBtn.setFocusable(true);
                    cancelBtn.setClickable(true);
                    postTitle.setFocusableInTouchMode(true);
                    postTitle.requestFocus();
                    postContents.setFocusableInTouchMode(true);
                    postContents.requestFocus();
                    break;
                case R.id.delete:
                    Toast.makeText(MyPostReadActivity.this, "삭제", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };
}
