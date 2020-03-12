package com.example.inhamind.Notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.NoticeAdapters;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Notice;
import com.example.inhamind.Models.User;
import com.example.inhamind.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoticeListActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private Intent intent;
    private RecyclerView mRecyclerView;
    private NoticeAdapters noticeAdapters;
    private List<Notice> mDatas;
    private Context context;
    private ImageButton writeButton;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        context = getApplicationContext();
        intent = getIntent();
        user = intent.getParcelableExtra(DataName.user);

        findViewById(R.id.notice_close_button).setOnClickListener(this);
        writeButton = findViewById(R.id.notice_write_button);
        writeButton.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.notice_recyclerview);

        if (user != null && user.getStudentID().equals(DataName.managerID)) {
            writeButton.setVisibility(View.VISIBLE);
            writeButton.setClickable(true);
            writeButton.setFocusable(true);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notice_close_button:
                this.finish();
                return;
            case R.id.notice_write_button:
                startActivity(new Intent(context, NoticeWirteActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatas = new ArrayList<>();
        mStore.collection(FirebaseID.notice)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                String noticeID = String.valueOf(shot.get(FirebaseID.noticeID));
                                String title = String.valueOf(shot.get(FirebaseID.title));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String name = String.valueOf(shot.get(FirebaseID.name));
                                Timestamp timestamp = (Timestamp) shot.get(FirebaseID.timestamp);
                                Notice data = new Notice(documentID, noticeID, name, title, contents, timestamp);
                                mDatas.add(data);
                            }
                            noticeAdapters = new NoticeAdapters(mDatas, context);
                            mRecyclerView.setAdapter(noticeAdapters);
                        }
                    }
                });

    }
}
