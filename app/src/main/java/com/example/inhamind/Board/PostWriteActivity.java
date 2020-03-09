package com.example.inhamind.Board;

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

public class PostWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText mTitle, mContents;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        findViewById(R.id.done_button).setOnClickListener(this);
        findViewById(R.id.close_button).setOnClickListener(this);

        mTitle = findViewById(R.id.post_title);
        mContents = findViewById(R.id.post_contents);

        //현재 user의 학번 불러오기
        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    studentID = (String) task.getResult().get(FirebaseID.studentID);
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
            case R.id.close_button:
                this.finish();
                break;

            case R.id.done_button:
                if (mUser != null) {
                    String postId = mStore.collection(FirebaseID.post).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(FirebaseID.documnetID, mUser.getUid());
                    data.put(FirebaseID.studentID, studentID);
                    data.put(FirebaseID.title, mTitle.getText().toString());
                    data.put(FirebaseID.contents, mContents.getText().toString());
                    data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
                    mStore.collection(FirebaseID.post).document(postId).set(data, SetOptions.merge());
                    finish();
                    Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
