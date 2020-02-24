package com.example.inhamind;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;

public class MyPage extends AppCompatActivity {
    ImageView userimage = (ImageView)findViewById(R.id.user_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        userimage.setBackground(new ShapeDrawable(new OvalShape())); //동그랗게 라운딩
        userimage.setClipToOutline(true);


    }
}
