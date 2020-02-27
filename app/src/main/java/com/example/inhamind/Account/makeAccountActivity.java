package com.example.inhamind.Account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.inhamind.EmailSend.MailSend;
import com.example.inhamind.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class makeAccountActivity extends LoginActivity {
    LayoutInflater dialog;
    View dialogLayout; //layout을 담을 View
    Dialog authDialog;

    // final Random rand = new Random();
    //        final TextView textGenerateNumber = (TextView)findViewById(R.id.generatenumber);
    //        textGenerateNumber.setText(String.valueOf(rand.nextInt(9000)+1000));
    EditText name_input;
    EditText student_id_input;
    EditText confirm_num_input;
    EditText pwd_join_input;
    EditText pwd_join_confirm;
    Button certification_button;
    Button confrim_button;

    TextView pswd_confirm;
    TextView count_view;
    TextView alert_messege;

    private Button btn;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    private String authCode;

    final String FIRESTORE_TAG = "[FIRESTORE_TAG]";
    private int checkPwdLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);


        name_input = (EditText) findViewById(R.id.sign_up_input_name);
        student_id_input = (EditText) findViewById(R.id.sign_up_student_id);
        confirm_num_input = (EditText) findViewById(R.id.sign_up_confirm);
        pwd_join_input = (EditText) findViewById(R.id.sign_up_input_pswd);
        pwd_join_confirm = (EditText) findViewById(R.id.sign_up_input_pswd_confirm);

        count_view = (TextView) findViewById(R.id.count_view);
        pswd_confirm = (TextView) findViewById(R.id.repswd_confirm);

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

        certification_button = (Button) findViewById(R.id.student_id_certification);
        certification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authCode = "1";
                MailSend mailSend = new MailSend(makeAccountActivity.this, student_id_input.getText().toString(), authCode);
                mailSend.sendMail();
            }
        }); //TODO : 이메일 인증하기

        confrim_button = (Button) findViewById(R.id.student_id_confrim);
        confrim_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // TODO: 인증번호 맞는지 확인하기
                String inputAuthCode = confirm_num_input.getText().toString();
                if(inputAuthCode.equals(authCode)){
                    Toast.makeText(makeAccountActivity.this, "인증 되었습니다.", Toast.LENGTH_SHORT).show();
                    confirm_num_input.setClickable(false);
                    confirm_num_input.setFocusable(false);
                    confirm_num_input.setTextColor(Color.GRAY);

                    student_id_input.setClickable(false);
                    student_id_input.setFocusable(false);
                    student_id_input.setTextColor(Color.GRAY);

                    certification_button.setText("인증");
                    certification_button.setTextColor(Color.GRAY);
                    certification_button.setClickable(false);
                    certification_button.setFocusable(false);

                    confrim_button.setText("완료");
                    confrim_button.setTextColor(Color.GRAY);
                    confrim_button.setClickable(false);
                    confrim_button.setFocusable(false);
                }
                else
                    Toast.makeText(makeAccountActivity.this, "코드를 확인해주세요!", Toast.LENGTH_SHORT).show();

            }
        });


        btn = (Button) findViewById(R.id.signUpButton);
        alert_messege = (TextView) findViewById(R.id.alert_messege);

        final Animation alertMessegeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alert_messege_animation);

        mAuth = FirebaseAuth.getInstance(); //Auth 생성
        mStore = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = name_input.getText().toString().trim();
                final String studentid = student_id_input.getText().toString().trim();
                final String confirmnum = confirm_num_input.getText().toString().trim();
                final String pwd = pwd_join_input.getText().toString().trim();
                final String confirmPswd = pwd_join_confirm.getText().toString().trim();

                if ((name != null && !name.isEmpty()) && (studentid != null && !studentid.isEmpty())
                        && (confirmnum != null && !confirmnum.isEmpty()) && (pwd != null && !pwd.isEmpty()) && (confirmPswd != null && !confirmPswd.isEmpty())
                && pswd_confirm.getText()=="일치") {//TODO: 비밀번호, 학번 확인
                    mAuth.createUserWithEmailAndPassword(studentid+"@inha.edu", pwd)
                            .addOnCompleteListener(makeAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user =mAuth.getCurrentUser();
                                        Map<String, Object> userMap = new HashMap<>(); //firestore 사용
                                        //userMap.put(FirebaseID.documentID, userMap.getUid()); //사용자 관리하기 위해
                                        userMap.put(FirebaseID.uID,user.getUid());
                                        userMap.put(FirebaseID.name, name);
                                        userMap.put(FirebaseID.studentID, studentid);
                                        userMap.put(FirebaseID.password, pwd);

                                        mStore.collection(FirebaseID.user)
                                                .document(user.getUid())
                                                .set(userMap, SetOptions.merge());//덮어쓰기(추가)
                                                 finish();
                                    } else {
                                        alert_messege.startAnimation(alertMessegeAnim);
                                    }
                                }
                            });
                }
            }
        });
    }

    public void countDown(String time) {
        long conversionTime = 0;

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getMin = time.substring(0, 2);   // 1, 2
        String getSecond = time.substring(2, 4);    // 3, 4

        // "00"이 아니고, 시,분,초의 첫번째 자리가 0 이면 제거
        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간(string->long)
        conversionTime = Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초) = 시간간격
        new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경(타이머가 종료될 때까지 동작)
            public void onTick(long millisUntilFinished) {
                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000));
                String min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

                // 밀리세컨드 단위
                //String millis = String.valueOf((getMin % (60 * 1000)) % 1000); // 몫

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                count_view.setText(min + ":" + second);
            }

            // 제한시간 종료시
            public void onFinish() {  // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지
                count_view.setText("종료");
                new AlertDialog.Builder(makeAccountActivity.this)
                        .setMessage("시간이 초과되었습니다")
                        .setTitle("회원가입을 재시도해주세요")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(makeAccountActivity.this,makeAccountActivity.class);
                                startActivity(intent);
                            }
                        })
//                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
                        .show();
            }
        }.start();
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
}
