package com.example.inhamind.Account;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.R;

public class PswdFind extends AppCompatActivity {

    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd_find);

        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(m);
    }

    RadioGroup.OnCheckedChangeListener m = new RadioGroup.OnCheckedChangeListener() {
        private RadioGroup group;
        private int checkedId;

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            this.group = group;
            this.checkedId = checkedId;
            Log.v("출력", checkedId + " ");

            if (group.getId() == R.id.rg) {
                RadioButton rv1 = findViewById(R.id.rv1);
                RadioButton rv2 = findViewById(R.id.rv2);
                switch (checkedId) {
                    case R.id.rv1:
                        rv1.setBackgroundResource(R.drawable.border_layout);
                        rv2.setBackgroundResource(R.color.colorBackground);
                        break;

                    case R.id.rv2:
                        rv1.setBackgroundResource(R.color.colorBackground);
                        rv2.setBackgroundResource(R.drawable.border_layout);
                        break;
                }
            }
        }
    };
}