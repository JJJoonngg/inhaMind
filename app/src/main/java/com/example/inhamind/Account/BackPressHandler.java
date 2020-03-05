package com.example.inhamind.Account;

import android.app.Activity;
import android.widget.Toast;

public class BackPressHandler {
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;
    // 종료시킬 Activity
    private Activity activity;

    public BackPressHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed(String msg, int time) {
        if (System.currentTimeMillis() > backKeyPressedTime + time) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide(msg);
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + time) {
            activity.finishAffinity();
            toast.cancel();
        }
    }

    private void showGuide(String msg) {
        toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
