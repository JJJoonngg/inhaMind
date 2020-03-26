package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.PostAdapters;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Post;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearhResultFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private RecyclerView mPostRecylerView;
    private PostAdapters postAdapters;
    private String contents, type;
    private List<Post> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searh_result, container, false);

        contents = getArguments().getString(DataName.data);
        type = getArguments().getString(DataName.type);

        if (type.equals("제목")) type = DataName.title;
        else if (type.equals("내용")) type = DataName.contents;
        else type = DataName.studentID;

        mPostRecylerView = view.findViewById(R.id.search_recylerview);
        view.findViewById(R.id.top_button).setOnClickListener(this);

        mDatas = new ArrayList<>();
        loadInformation();


        return view;
    }

    public void loadInformation() {
        firestore.collection(FirebaseID.post)
                .whereGreaterThanOrEqualTo(type, contents)
                .whereLessThanOrEqualTo(type, contents + '\uf8ff')
                .orderBy(type)
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
                                postAdapters = new PostAdapters(getContext(), mDatas);
                                mPostRecylerView.setAdapter(postAdapters);
                            }
                        }
                    }
                });

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
