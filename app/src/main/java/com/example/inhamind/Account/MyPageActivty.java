package com.example.inhamind.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MyPageActivty extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    TextView phone_change;
    TextView pswd_change;

    String name;
    String phoneNumber;
    String pswd;

    EditText user_name;
    EditText user_phone;
    EditText user_pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_activty);

        TextView logout = findViewById(R.id.logout);
        TextView secession = findViewById(R.id.secession_button);
        phone_change = findViewById(R.id.phone_change);
        pswd_change = findViewById(R.id.pswd_change);

        logout.setOnClickListener(this);
        secession.setOnClickListener(this);
        phone_change.setOnClickListener(this);
        pswd_change.setOnClickListener(this);

        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        user_pswd = findViewById(R.id.user_pswd);

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
                            user_name.setText(name);
                            user_phone.setText(phoneNumber);
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_change:
                //  startActivity(new Intent(MyPageActivty.this,));//TODO :수정하기 !
                break;
            case R.id.pswd_change:
                if (!user_pswd.getText().toString().equals(pswd)) {
                    Toast.makeText(MyPageActivty.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MyPageActivty.this, PswdRemake.class);
                    startActivity(intent);
                }
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
        }
    }
}