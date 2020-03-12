package com.example.inhamind.Common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.MainMyPostAdapters;
import com.example.inhamind.Adapters.MainNoticeAdapters;
import com.example.inhamind.Adapters.MainPostAdapters;
import com.example.inhamind.Models.Notice;
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

public class HomeFragment extends Fragment {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView noticeRecylerView, allPostRecylerView, myPostRecylerView;
    private MainPostAdapters allAdapters;
    private MainMyPostAdapters myAllAdapters;
    private MainNoticeAdapters noticeAdapters;
    private List<Post> postDatas;
    private List<Notice> noticeDatas;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        noticeRecylerView = view.findViewById(R.id.notice_list);
        allPostRecylerView = view.findViewById(R.id.all_post_list);
        myPostRecylerView = view.findViewById(R.id.my_post_list);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        noticeDatas = new ArrayList<>();
        mStore.collection(FirebaseID.notice)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            noticeDatas.clear();
                            int cnt = 0;
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                String noticeID = String.valueOf(shot.get(FirebaseID.noticeID));
                                String title = String.valueOf(shot.get(FirebaseID.title));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String name = String.valueOf(shot.get(FirebaseID.name));
                                Timestamp timestamp = (Timestamp) shot.get(FirebaseID.timestamp);
                                Notice data = new Notice(documentID, noticeID, name, title, contents, timestamp);
                                noticeDatas.add(data);
                                cnt++;
                                break;
                            }
                            if (cnt == 0) {
                                Notice data = new Notice(null, null, null, null, null, null);
                                noticeDatas.add(data);
                            }
                            noticeAdapters = new MainNoticeAdapters(noticeDatas, getContext());
                            noticeRecylerView.setAdapter(noticeAdapters);
                        }
                    }
                });

        postDatas = new ArrayList<>();
        mStore.collection(FirebaseID.post)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            postDatas.clear();
                            int cnt = 0;
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                if (cnt == 3) break;
                                Map<String, Object> shot = snap.getData();
                                String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                String postID = String.valueOf(shot.get(FirebaseID.postID));
                                String title = String.valueOf(shot.get(FirebaseID.title));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String studentID = String.valueOf(shot.get(FirebaseID.studentID));
                                String status = String.valueOf(shot.get(FirebaseID.status));
                                Timestamp timestamp = (Timestamp) shot.get(FirebaseID.timestamp);
                                Post data = new Post(documentID, postID, title, contents, studentID, status, timestamp);
                                postDatas.add(data);
                                cnt++;
                            }
                            if (cnt < 3) {
                                while (true) {
                                    if (cnt == 3) break;
                                    Post data = new Post(null, null, null, null, null, null, null);
                                    postDatas.add(data);
                                    cnt++;
                                }
                            }
                            allAdapters = new MainPostAdapters(postDatas, getContext());
                            allPostRecylerView.setAdapter(allAdapters);
                        }
                    }
                });

        if (mUser != null) {
            postDatas = new ArrayList<>();
            mStore.collection(FirebaseID.post)
                    .whereEqualTo(FirebaseID.documnetID, mUser.getUid())
                    .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (queryDocumentSnapshots != null) {
                                postDatas.clear();
                                int cnt = 0;
                                for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                    if (cnt == 3) break;
                                    Map<String, Object> shot = snap.getData();
                                    String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                    String postID = String.valueOf(shot.get(FirebaseID.postID));
                                    String title = String.valueOf(shot.get(FirebaseID.title));
                                    String contents = String.valueOf(shot.get(FirebaseID.contents));
                                    String studentID = String.valueOf(shot.get(FirebaseID.studentID));
                                    String status = String.valueOf(shot.get(FirebaseID.status));
                                    Timestamp timestamp = (Timestamp) shot.get(FirebaseID.timestamp);
                                    Post data = new Post(documentID, postID, title, contents, studentID, status, timestamp);
                                    postDatas.add(data);
                                    cnt++;
                                }
                                if (cnt < 3) {
                                    while (true) {
                                        if (cnt == 3) break;
                                        Post data = new Post(null, null, null, null, null, null, null);
                                        postDatas.add(data);
                                        cnt++;
                                    }
                                }
                                myAllAdapters = new MainMyPostAdapters(postDatas, getContext());
                                myPostRecylerView.setAdapter(myAllAdapters);
                            }
                        }
                    });
        }
    }

}