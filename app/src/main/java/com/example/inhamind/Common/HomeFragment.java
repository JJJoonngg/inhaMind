package com.example.inhamind.Common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.MainMyPostAdapters;
import com.example.inhamind.Adapters.MainPostAdapters;
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
    private RecyclerView allPostRecylerView, myPostRecylerView;
    private MainPostAdapters allAdapters;
    private MainMyPostAdapters myAllAdapters;
    private List<Post> mDatas;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Fragment fabFragment = new FloatingButtonFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fab_layout, fabFragment).commitAllowingStateLoss();

        allPostRecylerView = view.findViewById(R.id.all_post_list);
        myPostRecylerView = view.findViewById(R.id.my_post_list);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(FirebaseID.post)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
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
                                mDatas.add(data);
                                cnt++;
                            }
                            allAdapters = new MainPostAdapters(mDatas, getContext());
                            allPostRecylerView.setAdapter(allAdapters);
                        }
                    }
                });

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
                                    mDatas.add(data);
                                    cnt++;
                                }
                                myAllAdapters = new MainMyPostAdapters(mDatas, getContext());
                                myPostRecylerView.setAdapter(myAllAdapters);
                            }
                        }
                    });
        }
    }

}