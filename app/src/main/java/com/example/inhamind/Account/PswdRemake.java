package com.example.inhamind.Account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.FirebaseID;
import com.example.inhamind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PswdRemake extends AppCompatActivity {
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    EditText repswd;
    EditText repswd_confirm;
    TextView confirm;
    Button repswd_button;

    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{8,20}$"); // 8자리 ~ 20자리까지 가능

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd_remake);

        repswd = findViewById(R.id.repswd);
        repswd_confirm = findViewById(R.id.repswd_confirm_input);
        confirm = findViewById(R.id.repswd_confirm_output);
        repswd_button = findViewById(R.id.repswd_button);

        repswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repswd.getText().toString().equals(repswd_confirm.getText().toString())) {
                    confirm.setText("일치");
                } else {
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
                if (repswd.getText().toString().equals(repswd_confirm.getText().toString())) {
                    confirm.setText("일치");
                } else {
                    confirm.setText("불일치");
                    confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repswd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm.getText() == "일치" && validatePassword(repswd.getText().toString()) == true) {
                    //현재 user의 정보 불러오기
                    if (mUser != null) {
                        mStore.collection(FirebaseID.user).document(mUser.getUid())
                                .update("password", repswd.getText().toString());
                        Toast.makeText(PswdRemake.this, "비밀번호를 변경하였습니다", Toast.LENGTH_SHORT).show();
                        finish();
                        //TODO : 비밀번호 바꿨으면 바꾸지 못하게
                    }
                }
            }
        });
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }
}
