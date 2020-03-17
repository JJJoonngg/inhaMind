package com.example.inhamind.Account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.inhamind.EmailSend.MailSend;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Common.MainActivity;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MakeAccountActivity extends LoginActivity implements View.OnClickListener {
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{8,20}$"); // 8자리 ~ 20자리까지 가능

    EditText name_input;
    EditText student_id_input;
    EditText confirm_num_input;
    EditText pwd_join_input;
    EditText pwd_join_confirm;
    Button certification_button;
    Button confirm_button;

    TextView pswd_confirm;
    TextView count_view;
    TextView alert_messege;
    TextView count_down;

    private Button btn;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    private String authCode;

    final int Thousand = 1000;
    boolean isCounterRunning = false;
    boolean isClickedButton = false;



    CountDownTimer countDownTimer = new CountDownTimer(Thousand * 300, Thousand) {
        @Override
        public void onTick(long m) {
            count_down.setText(String.format(Locale.getDefault(), "%02d : %02d", (m / 1000L) / 60, (m / 1000L) % 60));
        }

        @Override
        public void onFinish() {
            authCode = createEmailCode();
            count_down.setText("");
            Toast.makeText(MakeAccountActivity.this, "인증코드를 재전송 해주세요.", Toast.LENGTH_SHORT).show();
            isCounterRunning = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        name_input = findViewById(R.id.sign_up_input_name);
        student_id_input = findViewById(R.id.sign_up_student_id);
        confirm_num_input = findViewById(R.id.sign_up_confirm);
        pwd_join_input = findViewById(R.id.sign_up_input_pswd);
        pwd_join_confirm = findViewById(R.id.sign_up_input_pswd_confirm);

        count_view = findViewById(R.id.count_view);
        pswd_confirm = findViewById(R.id.repswd_confirm);
        count_down = findViewById(R.id.count_down_time);

        certification_button = findViewById(R.id.student_id_certification);
        certification_button.setOnClickListener(this);
        confirm_button = findViewById(R.id.student_id_confirm);
        confirm_button.setOnClickListener(this);
        btn = findViewById(R.id.signUpButton);
        btn.setOnClickListener(this);

        pwd_join_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pwd_join_input.getText().toString().equals(pwd_join_confirm.getText().toString())) {
                    pswd_confirm.setText("일치");
                    pswd_confirm.setTextColor(Color.LTGRAY);
                } else {
                    pswd_confirm.setText("불일치");
                    pswd_confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pwd_join_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pwd_join_input.getText().toString().equals(pwd_join_confirm.getText().toString())) {
                    pswd_confirm.setText("일치");
                    pswd_confirm.setTextColor(Color.LTGRAY);
                } else {
                    pswd_confirm.setText("불일치");
                    pswd_confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mAuth = FirebaseAuth.getInstance(); //Auth 생성
        mStore = FirebaseFirestore.getInstance();
    }

    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newCode = new String();
        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }
        return newCode;
    }

    public void edittextStatusSetting(EditText e, boolean status, boolean c) {
        e.setFocusable(status);
        e.setFocusable(status);
        if (c) e.setTextColor(Color.GRAY);
        else e.setTextColor(Color.BLACK);
    }

    public void buttonStatusSetting(Button b, boolean status, boolean c, String s) {
        b.setClickable(status);
        b.setFocusable(status);
        b.setText(s);
        if (c) b.setTextColor(Color.GRAY);
        else b.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_id_certification:
                if (isClickedButton) {
                    edittextStatusSetting(student_id_input, true, false);
                    student_id_input.setFocusableInTouchMode(true);
                    student_id_input.setText("");


                    authCode = createEmailCode();
                    isClickedButton = false;
                    buttonStatusSetting(certification_button, true, false, "인증하기");

                    if (!isCounterRunning) {
                        countDownTimer.cancel();
                        count_down.setText("");
                    }

                } else {
                    if (student_id_input.getText().toString().length() == 8) {
                        edittextStatusSetting(student_id_input, false, true);

                        authCode = createEmailCode();
                        MailSend mailSend = new MailSend(MakeAccountActivity.this, student_id_input.getText().toString(), authCode);
                        mailSend.sendMail();
                        isClickedButton = true;
                        certification_button.setText("재입력");

                        if (!isCounterRunning) {
                            countDownTimer.cancel();
                            count_down.setText("");
                            countDownTimer.start();
                        } else {
                            countDownTimer.start();
                        }
                    } else
                        Toast.makeText(MakeAccountActivity.this, "학번 8 자리를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.student_id_confirm:
                String inputAuthCode = confirm_num_input.getText().toString();
                if (inputAuthCode.equals(authCode)) {
                    Toast.makeText(MakeAccountActivity.this, "인증 되었습니다.", Toast.LENGTH_SHORT).show();

                    edittextStatusSetting(confirm_num_input, false, true);
                    edittextStatusSetting(student_id_input, false, true);

                    buttonStatusSetting(certification_button, false, true, "인증");
                    buttonStatusSetting(confirm_button, false, true, "완료");

                    count_down.setText("");
                    countDownTimer.cancel();
                    count_down.setClickable(false);
                    count_down.setFocusable(false);
                } else
                    Toast.makeText(MakeAccountActivity.this, "코드를 확인해주세요!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signUpButton:
                final String name = name_input.getText().toString().trim();
                final String studentid = student_id_input.getText().toString().trim();
                final String confirmnum = confirm_num_input.getText().toString().trim();
                final String pwd = pwd_join_input.getText().toString().trim();
                final String confirmPswd = pwd_join_confirm.getText().toString().trim();

                if ((name != null && !name.isEmpty()) && (studentid != null && !studentid.isEmpty())
                        && (confirmnum != null && !confirmnum.isEmpty()) && (pwd != null && !pwd.isEmpty()) && (confirmPswd != null && !confirmPswd.isEmpty())
                        && pswd_confirm.getText() == "일치") {
                    if(confirm_button.getText()=="확인"){
                        Toast.makeText(MakeAccountActivity.this, "학번 인증을 진행해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!validatePassword(pwd)) {
                        Toast.makeText(MakeAccountActivity.this, "비밀번호 형식을 지켜주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(studentid+"@inha.edu", pwd)
                            .addOnCompleteListener(MakeAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Map<String, Object> userMap = new HashMap<>(); //firestore 사용
                                        userMap.put(FirebaseID.documnetID, user.getUid()); //사용자 관리하기 위해
                                        userMap.put(FirebaseID.name, name);
                                        userMap.put(FirebaseID.studentID,  studentid);
                                        userMap.put(FirebaseID.password, pwd);
                                        userMap.put(FirebaseID.profileImageUrl, "data:image/jpeg;base64,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");
                                        mStore.collection(FirebaseID.user)
                                                .document(user.getUid())
                                                .set(userMap, SetOptions.merge());//덮어쓰기(추가)
                                        finish();
                                        startActivity(new Intent(MakeAccountActivity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(MakeAccountActivity.this,"이미 존재하는 학번입니다",Toast.LENGTH_SHORT).show();
                                         }
                                }
                            });
                }
                else
                    Toast.makeText(MakeAccountActivity.this,"이미 존재하는 학번입니다",Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }
}