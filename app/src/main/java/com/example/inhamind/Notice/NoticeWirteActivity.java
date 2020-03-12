package com.example.inhamind.Notice;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class NoticeWirteActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText mTitle, mContents;
    private String writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_wirte);


        findViewById(R.id.write_done_button).setOnClickListener(this);
        findViewById(R.id.write_close_button).setOnClickListener(this);

        mTitle = findViewById(R.id.post_title);
        mContents = findViewById(R.id.post_contents);

        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    writer = (String) task.getResult().get(FirebaseID.name);
                                }
                            }

                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_close_button:
                this.finish();
                break;

            case R.id.write_done_button:
                if (mUser != null) {
                    if (mTitle.length() == 0)
                        Toast.makeText(this, "제목을 입력해야지 공지사항인데.", Toast.LENGTH_SHORT).show();
                    else if (mContents.length() == 0)
                        Toast.makeText(this, "내용또한 입력해야지 공지사항이라니깐?", Toast.LENGTH_SHORT).show();
                    else {
                        String noticeID = mStore.collection(FirebaseID.notice).document().getId();
                        Map<String, Object> data = new HashMap<>();
                        data.put(FirebaseID.documnetID, mUser.getUid());
                        data.put(FirebaseID.noticeID, noticeID);
                        data.put(FirebaseID.name, writer);
                        data.put(FirebaseID.title, mTitle.getText().toString());
                        data.put(FirebaseID.contents, mContents.getText().toString());
                        data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
                        mStore.collection(FirebaseID.notice).document(noticeID).set(data, SetOptions.merge());
                        finish();
                        Toast.makeText(this, "공지사항 등록 완료!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
