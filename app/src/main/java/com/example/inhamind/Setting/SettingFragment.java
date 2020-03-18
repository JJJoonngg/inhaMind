package com.example.inhamind.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inhamind.Account.MyPageActivty;
import com.example.inhamind.Account.ServiceCenter;
import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.User;
import com.example.inhamind.Notice.NoticeListActivity;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private TextView studentName, studentID, version;
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    private User user;
    private Intent intent;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        context = getContext();

        studentName = view.findViewById(R.id.student_name);
        studentID = view.findViewById(R.id.student_id);
        version = view.findViewById(R.id.version);

        view.findViewById(R.id.my_page).setOnClickListener(this);
        view.findViewById(R.id.my_post).setOnClickListener(this);
        view.findViewById(R.id.my_reservation).setOnClickListener(this);
        view.findViewById(R.id.log_out).setOnClickListener(this);
        view.findViewById(R.id.notice).setOnClickListener(this);
        view.findViewById(R.id.customer_service).setOnClickListener(this);
        view.findViewById(R.id.information).setOnClickListener(this);
        view.findViewById(R.id.terms_of_service).setOnClickListener(this);
        view.findViewById(R.id.app_setting).setOnClickListener(this);

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

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_page:
                intent = new Intent(context, MyPageActivty.class);
                intent.putExtra(DataName.user, user);
                startActivity(intent);
                break;

            case R.id.my_post:
                break;

            case R.id.my_reservation:
                break;

            case R.id.log_out:
                break;

            case R.id.notice:
                intent = new Intent(context, NoticeListActivity.class);
                intent.putExtra(DataName.user, user);
                startActivity(intent);
                break;

            case R.id.customer_service:
                startActivity(new Intent(context, ServiceCenter.class));
                break;

            case R.id.information:
                break;

            case R.id.terms_of_service:
                break;

            case R.id.app_setting:
                break;


        }

    }
}
