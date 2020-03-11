package com.example.inhamind.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Board.MyPostListActivity;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageActivty extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;

    boolean isClickedButton = false;
    TextView phoneChange;
    TextView pswdChange;

    String name;
    String phoneNumber;
    String pswd;

    EditText userName;
    EditText userPhone;
    EditText userPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_activty);

        TextView logout = findViewById(R.id.logout);
        TextView secession = findViewById(R.id.secession_button);
        phoneChange = findViewById(R.id.phone_change);
        pswdChange = findViewById(R.id.pswd_change);

        findViewById(R.id.my_post).setOnClickListener(this);

        logout.setOnClickListener(this);
        secession.setOnClickListener(this);
        phoneChange.setOnClickListener(this);
        pswdChange.setOnClickListener(this);

        userName = findViewById(R.id.user_name);
        userPhone = findViewById(R.id.user_phone);
        userPswd = findViewById(R.id.user_pswd);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mStore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_change:
                break;
            case R.id.pswd_change:
                changePassword();
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivty.this);
                builder.setTitle("로그아웃 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MyPageActivty.this, LoginActivity.class));
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
                break;
            case R.id.secession_button:
                AlertDialog.Builder build = new AlertDialog.Builder(MyPageActivty.this);
                build.setTitle("회원탈퇴 하시겠습니까?");
                Intent intent = new Intent(MyPageActivty.this, Secession.class);
                startActivity(intent);
                break;

            case R.id.my_post:
                startActivity(new Intent(this, MyPostListActivity.class));
                break;
        }
    }

    public void clickSetting() {
        if (isClickedButton) {
            edittextStatusSetting(userPswd, true, false);
            userPswd.setFocusableInTouchMode(true);
            userPswd.setHint("현재 비밀번호를 입력해주세요");

            isClickedButton = false;
            pswdChange.setText("변경하기");
        } else {
            userPswd.setHint("");
            edittextStatusSetting(userPswd, false, true);
            isClickedButton = true;
            pswdChange.setText("재변경하기");
        }
    }

    public void changePassword() {
        if (pswdChange.getText() == "재변경하기") {
            clickSetting();
        } else if (!userPswd.getText().toString().equals(pswd)) {
            Toast.makeText(MyPageActivty.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), PswdRemake.class);
            startActivityForResult(intent, 101);
        }
    }


    public void edittextStatusSetting(EditText e, boolean status, boolean c) {
        e.setClickable(status);
        e.setFocusable(status);
        if (c) e.setTextColor(Color.GRAY);
        else e.setTextColor(Color.BLACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                clickSetting();
            }
        } else {
            Toast.makeText(MyPageActivty.this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //현재 user의 정보 불러오기
        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    name = (String) task.getResult().get(FirebaseID.name);
                                    pswd = (String) task.getResult().get(FirebaseID.password);
                                    phoneNumber = (String) task.getResult().get(FirebaseID.phonenumber);
                                }
                            }
                            userName.setText(name);
                            userPhone.setText(phoneNumber);
                        }
                    });
        }
    }
}