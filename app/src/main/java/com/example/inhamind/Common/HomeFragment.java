package com.example.inhamind.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inhamind.Account.MyPageActivty;
import com.example.inhamind.Adapters.MainMyPostAdapters;
import com.example.inhamind.Adapters.MainNoticeAdapters;
import com.example.inhamind.Adapters.MainPostAdapters;
import com.example.inhamind.Board.PostWriteActivity;
import com.example.inhamind.Board.SearchActivity;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Notice;
import com.example.inhamind.Models.Post;
import com.example.inhamind.Models.User;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private RecyclerView noticeRecylerView, allPostRecylerView, myPostRecylerView;
    private MainPostAdapters allAdapters;
    private MainMyPostAdapters myAllAdapters;
    private MainNoticeAdapters noticeAdapters;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Post> allPostDatas, postDatas;
    private List<Notice> noticeDatas;

    private User user;
    private Intent intent;
    private Context context;
    private Bundle bundle;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();
        bundle = this.getArguments();
        if (bundle != null)
            user = bundle.getParcelable(DataName.user);

        noticeRecylerView = view.findViewById(R.id.notice_list);
        allPostRecylerView = view.findViewById(R.id.all_post_list);
        myPostRecylerView = view.findViewById(R.id.my_post_list);

        view.findViewById(R.id.main_button).setOnClickListener(this);
        view.findViewById(R.id.write_button).setOnClickListener(this);
        view.findViewById(R.id.search_button).setOnClickListener(this);
        view.findViewById(R.id.my_page_button).setOnClickListener(this);

        //스플래쉬 이미지 추가하면 삭제할 부분
        if (mUser != null) {
            mStore.collection(FirebaseID.user).document(mUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    String documentID = (String) task.getResult().get(FirebaseID.documnetID);
                                    String studentID = (String) task.getResult().get(FirebaseID.studentID);
                                    String name = (String) task.getResult().get(FirebaseID.name);
                                    String profileImageUrl = (String) task.getResult().get(FirebaseID.profileImageUrl);
                                    user = new User(documentID, name, studentID, profileImageUrl);
                                }
                            }

                        }
                    });
        }

        loadInformation();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadInformation();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void loadInformation() {

        noticeDatas = new ArrayList<>();
        mStore.collection(FirebaseID.notice)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                noticeDatas.clear();
                                int cnt = 0;
                                for (DocumentSnapshot snap : task.getResult()) {
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

                    }
                });

        allPostDatas = new ArrayList<>();
        mStore.collection(FirebaseID.post)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                allPostDatas.clear();
                                int cnt = 0;
                                for (DocumentSnapshot snap : task.getResult()) {
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
                                    allPostDatas.add(data);
                                    cnt++;
                                }
                                if (cnt < 3) {
                                    while (true) {
                                        if (cnt == 3) break;
                                        Post data = new Post(null, null, null, null, null, null, null);
                                        allPostDatas.add(data);
                                        cnt++;
                                    }
                                }

                                allAdapters = new MainPostAdapters(allPostDatas, getContext());
                                allPostRecylerView.setAdapter(allAdapters);
                            }
                        }
                    }
                });
        if (mUser != null) {
            postDatas = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Post data = new Post(null, null, null, null, null, null, null);
                postDatas.add(data);
                myAllAdapters = new MainMyPostAdapters(postDatas, getContext());
                myPostRecylerView.setAdapter(myAllAdapters);
            }
            mStore.collection(FirebaseID.post)
                    .whereEqualTo(FirebaseID.documnetID, mUser.getUid())
                    .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    postDatas.clear();
                                    int cnt = 0;
                                    for (DocumentSnapshot snap : task.getResult()) {
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
                        }
                    });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_button:
                Toast.makeText(context, "환영합니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.write_button:
                startActivity(new Intent(context, PostWriteActivity.class));
                break;
            case R.id.search_button:
                startActivity(new Intent(context, SearchActivity.class));
                break;
            case R.id.my_page_button:
                intent = new Intent(context, MyPageActivty.class);
                intent.putExtra(DataName.user, user);
                startActivity(intent);
                break;

        }

    }
}