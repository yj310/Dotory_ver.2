package com.mirim.dotory.student.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mirim.dotory.R;

public class StudentMyInfoActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my_info);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
    }
}