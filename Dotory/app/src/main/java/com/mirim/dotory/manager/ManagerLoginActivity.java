package com.mirim.dotory.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.LoginActivity;
import com.mirim.dotory.R;
import com.mirim.dotory.student.StudentBoardActivity;

public class ManagerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_login:
                    LogIn();
                    break;
            }
        }
    };


    public void LogIn() {
        Intent intent = new Intent(ManagerLoginActivity.this, ManagerBoardActivity.class);
        startActivity(intent);
        finish();
    }

}