package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inhamind.Adapters.PostAdapters;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Common.FloatingButtonFragment;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mPostRecylerView;
    private PostAdapters mAdapters;
    private List<Post> mDatas;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        Fragment fabFragment = new FloatingButtonFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fab_layout, fabFragment).commitAllowingStateLoss();

        mPostRecylerView = view.findViewById(R.id.board_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        view.findViewById(R.id.top_button).setOnClickListener(this);

        mDatas = new ArrayList<>();
        mStore.collection(FirebaseID.post)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                mDatas.clear();
                                for (DocumentSnapshot snap : task.getResult()) {
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
                                mAdapters = new PostAdapters(getContext(), mDatas);
                                mPostRecylerView.setAdapter(mAdapters);
                            }
                        }
                    }
                });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStore.collection(FirebaseID.post)
                        .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null) {
                                        mDatas.clear();
                                        for (DocumentSnapshot snap : task.getResult()) {
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
                                        mAdapters = new PostAdapters(getContext(), mDatas);
                                        mPostRecylerView.setAdapter(mAdapters);
                                    }
                                }
                            }
                        });

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_button:
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mPostRecylerView.getLayoutManager();
                mPostRecylerView.smoothScrollToPosition(0);
                break;
        }
    }
}
