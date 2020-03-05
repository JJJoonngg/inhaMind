package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private InitialStateFragment initialStateFragment = new InitialStateFragment();

    EditText mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, initialStateFragment).commitAllowingStateLoss();

        mContents = findViewById(R.id.search_input);

        findViewById(R.id.close_button).setOnClickListener(this);
        findViewById(R.id.search_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                this.finish();
                break;

            case R.id.secession_button:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}