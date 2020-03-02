package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.R;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTitle, mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        findViewById(R.id.done_button).setOnClickListener(this);
        findViewById(R.id.close_button).setOnClickListener(this);

        mTitle = findViewById(R.id.post_title);
        mContents = findViewById(R.id.post_contents);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                this.finish();
                break;

            case R.id.done_button:
                break;
        }

    }
}
