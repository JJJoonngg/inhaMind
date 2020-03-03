package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.Fragment.FloatingButtonFragment;
import com.example.inhamind.R;

public class BoardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        Fragment fabFragment = new FloatingButtonFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fab_layout, fabFragment).commitAllowingStateLoss();

        return view;
    }
}
