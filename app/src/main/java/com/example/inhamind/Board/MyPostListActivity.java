package com.example.inhamind.Board;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.MyPostAdapters;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyPostListActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView mRecyclerView;
    private MyPostAdapters myPostAdapters;
    private List<Post> mDatas;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        context = getApplicationContext();

        findViewById(R.id.my_post_close_button).setOnClickListener(this);
        findViewById(R.id.my_post_setting_button).setOnClickListener(this);

        mRecyclerView = findViewById(R.id.my_post_recyclerview);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_post_close_button:
                this.finish();
                break;
            case R.id.my_post_setting_button:
                Toast.makeText(this, "설정 기능 구현 예정", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mUser != null) {
            mDatas = new ArrayList<>();
            mStore.collection(FirebaseID.post)
                    .whereEqualTo(FirebaseID.documnetID, mUser.getUid())
                    .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (queryDocumentSnapshots != null) {
                                mDatas.clear();
                                for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                    Map<String, Object> shot = snap.getData();
                                    String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                    String postID = String.valueOf(shot.get(FirebaseID.postID));
                                    String title = String.valueOf(shot.get(FirebaseID.title));
                                    String contents = String.valueOf(shot.get(FirebaseID.contents));
                                    String studentID = String.valueOf(shot.get(FirebaseID.studentID));
                                    String status = String.valueOf(shot.get(FirebaseID.status));
                                    Timestamp timestamp = (Timestamp) shot.get(FirebaseID.timestamp);
                                    Post data = new Post(documentID, postID, title, contents, studentID, status, timestamp);
                                    mDatas.add(data);
                                }
                                myPostAdapters = new MyPostAdapters(mDatas, context);
                                mRecyclerView.setAdapter(myPostAdapters);
                            }
                        }
                    });
        }
    }
}