package com.example.inhamind;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener {
    EditText authEmail;
    Button authBtn;

    /*Dialog에 관련된 필드*/

    LayoutInflater dialog; //LayoutInflater
    View dialogLayout; //layout을 담을 View
    Dialog authDialog; //dialog 객체


    /*카운트 다운 타이머에 관련된 필드*/

    TextView time_counter; //시간을 보여주는 TextView
    EditText emailAuth_number; //인증 번호를 입력 하는 칸
    Button emailAuth_btn; // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    private String authCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());


        authEmail = (EditText) findViewById(R.id.emailAuth);
        authBtn = (Button) findViewById(R.id.btnAuth);
        authBtn.setOnClickListener(this);
        authCode = createEmailCode();
    }

    public void countDownTimer() { //카운트 다운 메소드

        time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }


            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                authDialog.cancel();

            }
        }.start();

        emailAuth_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MailSend mailSend = new MailSend(this, authEmail.getText().toString(), authCode);

        switch (v.getId()) {
            case R.id.btnAuth:
                mailSend.sendMail();
                dialog = LayoutInflater.from(this);
                dialogLayout = dialog.inflate(R.layout.main_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                authDialog = new Dialog(this); //Dialog 객체 생성
                authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
                authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
                authDialog.setOnCancelListener(this); //다이얼로그를 닫을 때 일어날 일을 정의하기 위해 onCancelListener 설정
                authDialog.show(); //Dialog를 나타내어 준다.
                countDownTimer();
                break;

            case R.id.emailAuth_btn: //다이얼로그 내의 인증번호 인증 버튼을 눌렀을 시
                String user_answer = emailAuth_number.getText().toString();
                Toast.makeText(this, user_answer + ", " + authCode, Toast.LENGTH_SHORT).show();
                if (user_answer.equals(authCode)) {
                    Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                    authDialog.dismiss();
                } else {
                    Toast.makeText(this, "이메일 인증 실패", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    } //다이얼로그 닫을 때 카운트 다운 타이머의 cancel()메소드 호출


    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newCode = new String();
        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }
        return newCode;
    }

}