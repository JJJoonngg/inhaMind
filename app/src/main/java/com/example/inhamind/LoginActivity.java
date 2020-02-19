package org.techtown.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    TextView alert_messege;
    TextView make_account_text;
    TextView find_pwd;

    EditText EditTextId;
    EditText EditTextPswd;
    ImageButton login_button;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Animation alertMessegeAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alert_messege_animation);


        mAuth = FirebaseAuth.getInstance();

        EditTextId = (EditText)findViewById(R.id.student_id);
        EditTextPswd = (EditText)findViewById(R.id.student_pswd);
        find_pwd = (TextView)findViewById(R.id.pswd_find);
        find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PswdFind.class);
                startActivity(intent);
            }
        });

        make_account_text = (TextView)findViewById(R.id.make_account);
        make_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,makeAccountActivity.class);
                startActivity(intent);
            }
        });

        //EditTextEmail.setText("aaaa@aaa.aaa");
        //EditTextPswd.setText("aaaa123");

        login_button = (ImageButton)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pswd= EditTextPswd.getText().toString().trim();
                String studentid= EditTextId.getText().toString().trim();

                final String TAG = "LOGIN_ACTIVITY";
                if(studentid!=null&&!studentid.isEmpty()&&pswd!=null&&!pswd.isEmpty()){
                    mAuth.signInWithEmailAndPassword(studentid, pswd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "createUserWithEmail:success");


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        alert_messege=(TextView)findViewById(R.id.alert_messege);
                                        alert_messege.startAnimation(alertMessegeAnim);
                                    }

                                }
                            });}
            }
        });

    }

}

