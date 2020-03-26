package com.example.inhamind.Board;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.inhamind.Models.DataName;
import com.example.inhamind.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction = fragmentManager.beginTransaction();
    private InitialStateFragment initialStateFragment = new InitialStateFragment();

    private EditText mContents;
    private String contents, choiceCollection;
    private Spinner spinner;
    final private String[] list = {"제목", "내용", "학번"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        transaction.replace(R.id.frameLayout, initialStateFragment).commitAllowingStateLoss();

        mContents = findViewById(R.id.search_input);
        spinner = findViewById(R.id.search_spinner);
        choiceCollection = "제목";

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                choiceCollection = list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        findViewById(R.id.write_close_button).setOnClickListener(this);
        findViewById(R.id.search_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_close_button:
                this.finish();
                break;

            case R.id.search_button:
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new SearhResultFragment();
                contents = mContents.getText().toString();
                if (contents != null) {
                    if (contents.length() < 2)
                        Toast.makeText(getApplicationContext(), "내용을 두 글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show();
                    else {
                        Bundle bundle = new Bundle(1);
                        bundle.putString(DataName.data, contents);
                        bundle.putString(DataName.type, choiceCollection);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "내용을 두 글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}