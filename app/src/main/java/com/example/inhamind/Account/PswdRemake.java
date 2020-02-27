package com.example.inhamind.Account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.R;

public class PswdRemake extends AppCompatActivity {
    EditText repswd;
    EditText repswd_confirm;
    TextView confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd_remake);

        repswd = (EditText)findViewById(R.id.repswd);
        repswd_confirm = (EditText)findViewById(R.id.repswd_confirm_input);
        confirm = (TextView)findViewById(R.id.repswd_confirm_output);

        repswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(repswd.getText().toString().equals(repswd_confirm.getText().toString())){
                    confirm.setText("일치");
                }
                else {
                    confirm.setText("불일치");
                    confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        repswd_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(repswd.getText().toString().equals(repswd_confirm.getText().toString())){
                    confirm.setText("일치");
                }
                else {
                    confirm.setText("불일치");
                    confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
