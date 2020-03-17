package com.example.inhamind.Common;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.Board.BoardFragment;
import com.example.inhamind.Chat.ChattingFragment;
import com.example.inhamind.R;
import com.example.inhamind.Setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private ChattingFragment chattingFragment = new ChattingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.board:
                    transaction.replace(R.id.frameLayout, boardFragment).commitAllowingStateLoss();
                    break;
                case R.id.chatting:
                    transaction.replace(R.id.frameLayout, chattingFragment).commitAllowingStateLoss();
                    break;
                case R.id.setting:
                    transaction.replace(R.id.frameLayout, settingFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}