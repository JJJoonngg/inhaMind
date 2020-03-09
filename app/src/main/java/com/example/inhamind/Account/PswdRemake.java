package com.example.inhamind.Account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PswdRemake extends MyPageActivty {
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    String pswd;

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


        //현재 user의 정보 불러오기
        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    pswd = (String) task.getResult().get(FirebaseID.password);
                                }
                            }
                        }
                    });
        }

        repswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repswd.getText().toString().equals(repswd_confirm.getText().toString())) {
                    confirm.setText("일치");
                    confirm.setTextColor(Color.LTGRAY);
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
                    confirm.setTextColor(Color.LTGRAY);
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
                if (validatePassword(repswd.getText().toString()) == true) {
                    if (confirm.getText() == "일치") {
                        if (!pswd.equals(repswd)) {
                            if (mUser != null) {
                                mStore.collection(FirebaseID.user).document(mUser.getUid())
                                        .update("password", repswd.getText().toString());
                                mUser.updatePassword(repswd.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(PswdRemake.this, "비밀번호를 변경하였습니다", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.putExtra("change", "confirm");

                                        setResult(Activity.RESULT_OK, intent);

                                        finish();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(PswdRemake.this, "현재 비밀번호와 동일합니다", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PswdRemake.this, "비밀번호가 일치하지 않습니다 ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PswdRemake.this, "비밀번호 형식을 지켜주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }
}