package com.mirim.dotory.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.R;

public class StudentJoinFirstActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_first);

        findViewById(R.id.btn_next).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_next:
                    NextPage();
                    break;
                case R.id.btn_back:
                    finish();
                    break;

            }
        }
    };


    public void NextPage() {

        Intent intent = new Intent(StudentJoinFirstActivity.this, StudentJoinSecondActivity.class);
        startActivity(intent);
    }

}