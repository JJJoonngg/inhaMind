package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inhamind.Adapters.PostAdapters;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearhResultFragment extends Fragment {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private RecyclerView mPostRecylerView;
    private PostAdapters postAdapters;
    private String contents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searh_result, container, false);
        contents = getArguments().getString(DataName.data);

        return view;
    }
}
