package com.example.inhamind.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;

import com.example.inhamind.Board.PostActivity;
import com.example.inhamind.Board.SearchActivity;
import com.example.inhamind.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FloatingButtonFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton float_menu, float_write, float_search;
    Animation fab_open, fab_close;
    Boolean openFlag = false;

    View fw, ff;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floating_button, container, false);

        context = container.getContext();

        float_menu = view.findViewById(R.id.float_menu);
        float_write = view.findViewById(R.id.float_write_post);
        float_search = view.findViewById(R.id.float_find_post);

        fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);

        fw = view.findViewById(R.id.float_write_post);
        ff = view.findViewById(R.id.float_find_post);

        //before Opened
        float_write.startAnimation(fab_close);
        float_search.startAnimation(fab_close);
        float_write.setClickable(false);
        float_search.setClickable(false);

        float_menu.setOnClickListener(this);
        float_write.setOnClickListener(this);
        float_search.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_menu:
                anim();
                break;
            case R.id.float_write_post:
                anim();
                startActivity(new Intent(context, PostActivity.class));
                break;
            case R.id.float_find_post:
                anim();
                startActivity(new Intent(context, SearchActivity.class));
                break;
        }
    }

    public void anim() {
        if (openFlag) {
            float_write.startAnimation(fab_close);
            float_search.startAnimation(fab_close);
            float_write.setClickable(false);
            float_search.setClickable(false);
            ff.setVisibility(View.VISIBLE);
            fw.setVisibility(View.VISIBLE);
            openFlag = false;
        } else {
            float_write.startAnimation(fab_open);
            float_search.startAnimation(fab_open);
            float_write.setClickable(true);
            float_search.setClickable(true);
            ff.setVisibility(View.VISIBLE);
            fw.setVisibility(View.VISIBLE);
            openFlag = true;
        }

    }
}
