package com.example.inhamind.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.MainActivity;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private boolean saveLoginData;
    private String id;
    private String pwd;

    private SharedPreferences appData;
    TextView make_account_text;
    TextView find_pwd;

    EditText EditTextId;
    EditText EditTextPswd;
    ImageButton login_button;
    CheckBox autoLogin;
    FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Animation alertMessegeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alert_messege_animation);

        mAuth = FirebaseAuth.getInstance();

        EditTextId = (EditText) findViewById(R.id.student_id);
        EditTextPswd = (EditText) findViewById(R.id.student_pswd);
        find_pwd = (TextView) findViewById(R.id.pswd_find);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        if(saveLoginData){
            EditTextId.setText(id);
            EditTextPswd.setText(pwd);
            autoLogin.setChecked(saveLoginData);
        }

        autoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 성공시 저장 처리, 예제는 무조건 저장
                save();
            }
        });


        find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PswdFind.class));
            }
        });

        make_account_text = (TextView) findViewById(R.id.make_account);
        make_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, makeAccountActivity.class));
            }
        });

        //EditTextId.setText("12131212");//test 위함
        //EditTextPswd.setText("qwer1234");

        login_button = (ImageButton) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ( EditTextId.getText().toString().length() == 8) {
                String studentid = EditTextId.getText().toString().trim();
                String pswd = EditTextPswd.getText().toString().trim();

                final String TAG = "LOGIN_ACTIVITY";
                if (studentid != null && !studentid.isEmpty() && pswd != null && !pswd.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(studentid + "@inha.edu", pswd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException e) {
                                            Toast.makeText(LoginActivity.this, "회원가입하지 않은 학번입니다.", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseNetworkException e) {
                                            Toast.makeText(LoginActivity.this, "Firebase NetworkException", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                                        }

                                    } else { //auto
                                        currentUser = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "로그인 성공" + "/" + currentUser.getEmail() + "/" + currentUser.getUid(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
                else{
                    Toast.makeText(LoginActivity.this,"8자리 학번을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", autoLogin.isChecked());
        editor.putString("ID", EditTextId.getText().toString().trim());
        editor.putString("PWD", EditTextPswd.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }


    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}

