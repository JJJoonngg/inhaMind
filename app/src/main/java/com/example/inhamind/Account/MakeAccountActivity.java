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