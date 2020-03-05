package com.example.inhamind.Board;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.R;

public class SearchActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private InitialStateFragment initialStateFragment = new InitialStateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, initialStateFragment).commitAllowingStateLoss();
    }
}
