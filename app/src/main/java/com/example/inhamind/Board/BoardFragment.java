package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.PostAdapters;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Common.FloatingButtonFragment;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardFragment extends Fragment {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView mPostRecylerView;
    private PostAdapters mAdapters;
    private List<Post> mDatas;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        Fragment fabFragment = new FloatingButtonFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fab_layout, fabFragment).commitAllowingStateLoss();

        mPostRecylerView = view.findViewById(R.id.board_recyclerview);

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
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String documentID = String.valueOf(shot.get(FirebaseID.documnetID));
                                String title = String.valueOf(shot.get(FirebaseID.title));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String studentID = String.valueOf(shot.get(FirebaseID.studentID));
                                String status = String.valueOf(shot.get(FirebaseID.status));
                                Post data = new Post(documentID, title, contents, studentID, status);
                                mDatas.add(data);
                            }
                            mAdapters = new PostAdapters(getContext(), mDatas);
                            mPostRecylerView.setAdapter(mAdapters);
                        }
                    }
                });
    }
}
