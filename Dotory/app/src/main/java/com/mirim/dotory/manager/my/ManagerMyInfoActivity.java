package com.mirim.dotory.manager.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mirim.dotory.R;

public class ManagerMyInfoActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_info);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }
}