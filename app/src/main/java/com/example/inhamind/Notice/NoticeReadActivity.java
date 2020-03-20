package com.example.inhamind.Notice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inhamind.Common.FirebaseID;
import com.example.inhamind.Models.DataName;
import com.example.inhamind.Models.Notice;
import com.example.inhamind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

public class NoticeReadActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    private EditText noticeTitle, noticeContents;
    private String title, contents, name;
    @ServerTimestamp
    private Timestamp timestamp;
    private Intent intent;
    private Notice notice;
    private Button doneBtn, cancelBtn;
    private ImageButton optionBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_read);

        intent = getIntent();
        notice = intent.getParcelableExtra(DataName.data);

        title = notice.getTitle();
        contents = notice.getContents();
        timestamp = notice.getTimestamp();

        doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(this);
        cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(this);

        noticeTitle = findViewById(R.id.notice_title);
        noticeContents = findViewById(R.id.notice_contents);

        noticeTitle.setText(title);
        noticeContents.setText(contents);

        findViewById(R.id.close_button).setOnClickListener(this);

        optionBtn = findViewById(R.id.option_button);

        if (mUser != null) {
            if (mUser.getUid().equals(DataName.managerDocumentID)){
                optionBtn.setFocusable(true);
                optionBtn.setClickable(true);
                optionBtn.setVisibility(View.VISIBLE);
            }
        }
        optionBtn.setOnClickListener(this);
    }

    public void edittextStatusSetting(EditText e, boolean status) {
        e.setClickable(status);
        e.setFocusable(status);
    }

    public void buttonStatusSetting(Button b, boolean status) {
        b.setClickable(status);
        b.setFocusable(status);
        b.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                this.finish();
                break;
            case R.id.option_button:
                PopupMenu popupMenu = new PopupMenu(NoticeReadActivity.this, view);
                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(listener);
                popupMenu.show();
                break;

            case R.id.cancel:
                noticeTitle.setText(title);
                noticeContents.setText(contents);
                buttonStatusSetting(doneBtn, false);
                buttonStatusSetting(cancelBtn, false);
                edittextStatusSetting(noticeTitle, false);
                edittextStatusSetting(noticeContents, false);
                break;
            case R.id.done:
                String newTitle = noticeTitle.getText().toString();
                String newContents = noticeContents.getText().toString();
                if (newTitle.length() == 0)
                    Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (newContents.length() == 0)
                    Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if (mUser != null) {
                        mStore.collection(FirebaseID.notice)
                                .document(notice.getNoticeID())
                                .update(FirebaseID.title, newTitle, FirebaseID.contents, newContents)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task != null) {
                                            Toast.makeText(NoticeReadActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    noticeTitle.setText(newTitle);
                    noticeContents.setText(newContents);
                    buttonStatusSetting(doneBtn, false);
                    buttonStatusSetting(cancelBtn, false);
                    edittextStatusSetting(noticeTitle, false);
                    edittextStatusSetting(noticeContents, false);
                }
                break;
        }
    }

    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.modify:
                    buttonStatusSetting(doneBtn, true);
                    buttonStatusSetting(cancelBtn, true);
                    cancelBtn.setClickable(true);
                    noticeTitle.setFocusableInTouchMode(true);
                    noticeTitle.requestFocus();
                    noticeContents.setFocusableInTouchMode(true);
                    noticeContents.requestFocus();
                    break;
                case R.id.delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeReadActivity.this);
                    builder.setTitle("공지 삭제 할껀가 자네");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mUser != null) {
                                mStore.collection(FirebaseID.notice)
                                        .document(notice.getNoticeID())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task != null) {
                                                    finish();
                                                    Toast.makeText(NoticeReadActivity.this, "공지 삭제 완료!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
            }
            return false;
        }
    };
}
