package com.example.inhamind.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.R;

public class Secession extends AppCompatActivity implements View.OnClickListener {
    private Button secession_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secession);

        secession_button = (Button) findViewById(R.id.secession_button);
        secession_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.secession_button:
                break;
        }
    }
}