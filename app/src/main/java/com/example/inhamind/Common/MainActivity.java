package com.example.inhamind.Common;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment;
    private BoardFragment boardFragment = new BoardFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private ChattingFragment chattingFragment = new ChattingFragment();

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    BottomNavigationView bottomNavigationView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigationView);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        homeFragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        if (user != null) bundle.putParcelable(DataName.user, user);
        homeFragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, homeFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.home:
                    Bundle bundle = new Bundle();
                    if (user != null) bundle.putParcelable(DataName.user, user);
                    homeFragment.setArguments(bundle);
                    transaction.replace(R.id.frameLayout, homeFragment).commit();
                    break;
                case R.id.board:
                    Bundle bundle1 = new Bundle();
                    if (user != null) bundle1.putParcelable(DataName.user, user);
                    boardFragment.setArguments(bundle1);
                    transaction.replace(R.id.frameLayout, boardFragment).commit();
                    break;
                case R.id.chatting:
                    transaction.replace(R.id.frameLayout, chattingFragment).commit();
                    break;
                case R.id.setting:
                    Bundle bundle2 = new Bundle();
                    if (user != null) bundle2.putParcelable(DataName.user, user);
                    settingFragment.setArguments(bundle2);
                    transaction.replace(R.id.frameLayout, settingFragment).commit();
                    break;
            }
            return true;
        }
    }
}