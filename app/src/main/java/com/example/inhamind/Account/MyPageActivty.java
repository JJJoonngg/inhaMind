package com.example.inhamind.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inhamind.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyPageActivty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_activty);

        TextView logout = (TextView)findViewById(R.id.logout);
        TextView secession = (TextView)findViewById(R.id.secession_button);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MyPageActivty.this,"로그아웃하시겠습니까?",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(MyPageActivty.this,LoginActivity.class));
        }
        });
            secession.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MyPageActivty.this,Secession.class);
                    startActivity(intent);
                }
            });
    }
}