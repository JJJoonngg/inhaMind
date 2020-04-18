package com.example.inhamind.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.inhamind.Account.LoginActivity;
import com.example.inhamind.Account.MyPageActivty;
import com.example.inhamind.Account.ServiceCenter;
import com.example.inhamind.Account.Tos;
import com.example.inhamind.Board.MyPostListActivity;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.User;
import com.example.inhamind.Notice.NoticeListActivity;
import com.example.inhamind.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        Bundle bundle = this.getArguments();
        if (bundle != null) user = bundle.getParcelable(DataName.user);
        if (user != null) Log.d("TAG", user.toString());

        studentName = view.findViewById(R.id.student_name);
        studentID = view.findViewById(R.id.student_id);
        version = view.findViewById(R.id.version);

        if (user != null) {
            studentName.setText(user.getName());
            studentID.setText(user.getStudentID());
        }
        view.findViewById(R.id.my_page).setOnClickListener(this);
        view.findViewById(R.id.my_post).setOnClickListener(this);
        view.findViewById(R.id.my_reservation).setOnClickListener(this);
        view.findViewById(R.id.log_out).setOnClickListener(this);
        view.findViewById(R.id.notice).setOnClickListener(this);
        view.findViewById(R.id.customer_service).setOnClickListener(this);
        view.findViewById(R.id.information).setOnClickListener(this);
        view.findViewById(R.id.terms_of_service).setOnClickListener(this);

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
                startActivity(new Intent(context, MyPostListActivity.class));
                break;

            case R.id.my_reservation:
                break;

            case R.id.log_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("로그아웃 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
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
                startActivity(new Intent(context, Tos.class));
                break;

        }

    }
}
