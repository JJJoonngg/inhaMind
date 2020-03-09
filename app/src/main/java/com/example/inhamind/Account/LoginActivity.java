package com.example.inhamind.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Common.MainActivity;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean saveLoginData;
    private String id;
    private String pwd;

    private SharedPreferences appData;
    TextView make_account;
    TextView find_pwd;
    EditText EditTextId;
    EditText EditTextPswd;
    ImageButton login_button;
    CheckBox autoLogin;

    FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private BackPressHandler backPressHandler = new BackPressHandler(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autoLogin = findViewById(R.id.auto_login);
        autoLogin.setOnClickListener(this);

        find_pwd = findViewById(R.id.pswd_find);
        find_pwd.setOnClickListener(this);

        make_account = findViewById(R.id.make_account);
        make_account.setOnClickListener(this);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        EditTextId = findViewById(R.id.student_id);
        EditTextPswd = findViewById(R.id.student_pswd);

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        if (saveLoginData) {
            EditTextId.setText(id);
            EditTextPswd.setText(pwd);
            autoLogin.setChecked(saveLoginData);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_login:
                save();
                break;
            case R.id.pswd_find:
                startActivity(new Intent(LoginActivity.this, PswdFind.class));
                break;
            case R.id.make_account:
                startActivity(new Intent(LoginActivity.this, MakeAccountActivity.class));
                break;
            case R.id.login_button:
                if (EditTextId.getText().toString().length() == 8) {
                    String studentid = EditTextId.getText().toString().trim();
                    String pswd = EditTextPswd.getText().toString().trim();

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
                                            Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                });

                        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");
                        Matcher m = p.matcher(pwd);
                        if (!m.find() && pwd.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"))
                            Toast.makeText(LoginActivity.this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "8자리 학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed("뒤로가기 버튼 한번 더 누르면 종료", 3000);
    }
}