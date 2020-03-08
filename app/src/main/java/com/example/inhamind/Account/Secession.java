package com.example.inhamind.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.EmailSend.MailSend;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Secession extends AppCompatActivity implements View.OnClickListener {
    private Button secession_button;
    EditText message;
    String studentId;
    String pswd;
    EditText secession_pswd;

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secession);

        secession_button = (Button) findViewById(R.id.secession_button);
        secession_button.setOnClickListener(this);

        message = findViewById(R.id.secession_message);
        secession_pswd = findViewById(R.id.secession_pswd);

        //현재 user의 정보 불러오기
        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    studentId = (String) task.getResult().get(FirebaseID.studentID);
                                    pswd = (String) task.getResult().get(FirebaseID.password);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.secession_button:
                if (mUser != null) {
                    if (secession_pswd.getText().toString().equals(pswd)) {
                        mUser.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            MailSend mailSend = new MailSend(Secession.this, "inhamindteam@gmail.com", studentId + "가 탈퇴한 이유 :\n" + message.getText().toString());
                                            mailSend.sessionsendMail();
                                            mStore.collection(FirebaseID.user)
                                                    .document(mUser.getUid())
                                                    .delete();
                                            Toast.makeText(Secession.this, "계정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Secession.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(Secession.this, "계정이 삭제되지 않았습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(Secession.this, "비밀번호가 틀렸습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}