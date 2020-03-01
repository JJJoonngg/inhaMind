package com.example.inhamind.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inhamind.R;
import com.google.firebase.auth.FirebaseAuth;


public class MyPageActivty extends AppCompatActivity implements View.OnClickListener {
    TextView phone_change;
    TextView pswd_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_activty);

            TextView logout = (TextView)findViewById(R.id.logout);
            TextView secession = (TextView)findViewById(R.id.secession_button);
            phone_change = (TextView)findViewById(R.id.phone_change);
            pswd_change = (TextView)findViewById(R.id.pswd_change);

            logout.setOnClickListener(this);
            secession.setOnClickListener(this);
            phone_change.setOnClickListener(this);
            pswd_change.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone_change:
              //  startActivity(new Intent(MyPageActivty.this,));//TODO :수정하기 !
                break;
            case R.id.pswd_change:
                startActivity(new Intent(MyPageActivty.this,PswdRemake.class));
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivty.this);
                builder.setTitle("로그아웃 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity( new Intent(MyPageActivty.this,LoginActivity.class));
                    }
                });
                builder.setNegativeButton("아니오",null);
                builder.create().show();
                break;
            case R.id.secession_button:
                AlertDialog.Builder build = new AlertDialog.Builder(MyPageActivty.this);
                build.setTitle("회원탈퇴 하시겠습니까?");
                Intent intent = new Intent(MyPageActivty.this,Secession.class);
                startActivity(intent);
                break;
        }
    }
}