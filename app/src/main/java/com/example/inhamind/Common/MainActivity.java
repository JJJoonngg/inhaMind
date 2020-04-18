package com.example.inhamind.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.Account.BackPressHandler;
import com.example.inhamind.Board.BoardFragment;
import com.example.inhamind.Chat.ChattingFragment;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.User;
import com.example.inhamind.R;
import com.example.inhamind.Setting.SettingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
=======
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;
>>>>>>> 97cb8de25d98a3f369df9649fc017b53898ac8be

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private BoardFragment boardFragment;
    private SettingFragment settingFragment;
    private ChattingFragment chattingFragment;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    BottomNavigationView bottomNavigationView;

    private BackPressHandler backPressHandler = new BackPressHandler(MainActivity.this);

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        if (user != null) bundle.putParcelable(DataName.user, user);
        homeFragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, homeFragment).commit();

<<<<<<< HEAD
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
=======
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        passPushTokenToServer();
>>>>>>> 97cb8de25d98a3f369df9649fc017b53898ac8be
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    if (homeFragment == null) {
                        homeFragment =  new HomeFragment();
                        Bundle bundle = new Bundle();
                        if (user != null) bundle.putParcelable(DataName.user, user);
                        homeFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment).commit();
                    }
                    if (homeFragment != null) fragmentManager.beginTransaction().show(homeFragment).commit();
                    if (boardFragment != null) fragmentManager.beginTransaction().hide(boardFragment).commit();
                    if (chattingFragment != null) fragmentManager.beginTransaction().hide(chattingFragment).commit();
                    if (settingFragment != null) fragmentManager.beginTransaction().hide(settingFragment).commit();
                    break;
                case R.id.board:
                    if (boardFragment == null) {
                        boardFragment =  new BoardFragment();
                        fragmentManager.beginTransaction().add(R.id.frameLayout, boardFragment).commit();
                    }
                    if (homeFragment != null) fragmentManager.beginTransaction().hide(homeFragment).commit();
                    if (boardFragment != null) fragmentManager.beginTransaction().show(boardFragment).commit();
                    if (chattingFragment != null) fragmentManager.beginTransaction().hide(chattingFragment).commit();
                    if (settingFragment != null) fragmentManager.beginTransaction().hide(settingFragment).commit();
                    break;
                case R.id.chatting:
                    if (chattingFragment == null) {
                        chattingFragment =  new ChattingFragment();
                        fragmentManager.beginTransaction().add(R.id.frameLayout, chattingFragment).commit();
                    }
                    if (homeFragment != null) fragmentManager.beginTransaction().hide(homeFragment).commit();
                    if (boardFragment != null) fragmentManager.beginTransaction().hide(boardFragment).commit();
                    if (chattingFragment != null) fragmentManager.beginTransaction().show(chattingFragment).commit();
                    if (settingFragment != null) fragmentManager.beginTransaction().hide(settingFragment).commit();
                    break;
                case R.id.setting:
                    if(settingFragment == null) {
                        settingFragment = new SettingFragment();
                        Bundle bundle1 = new Bundle();
                        if (user != null) bundle1.putParcelable(DataName.user, user);
                        settingFragment.setArguments(bundle1);
                        fragmentManager.beginTransaction().add(R.id.frameLayout, settingFragment).commit();
                    }
                    if (homeFragment != null) fragmentManager.beginTransaction().hide(homeFragment).commit();
                    if (boardFragment != null) fragmentManager.beginTransaction().hide(boardFragment).commit();
                    if (chattingFragment != null) fragmentManager.beginTransaction().hide(chattingFragment).commit();
                    if (settingFragment != null) fragmentManager.beginTransaction().show(settingFragment).commit();
                    break;
            }
            return true;
        }
    }
<<<<<<< HEAD

    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed("뒤로가기 버튼 한번 더 누르면 종료", 3000);
=======
    void passPushTokenToServer(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    return;
                }
                String token = task.getResult().getToken();
                Map<String,Object> map  = new HashMap<>();
                map.put("pushToken",token);
                FirebaseFirestore.getInstance().collection(FirebaseID.user).document(uid).update(map);
            }
        });


>>>>>>> 97cb8de25d98a3f369df9649fc017b53898ac8be
    }
}