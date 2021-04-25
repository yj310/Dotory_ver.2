package com.mirim.dotory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_login:
                    LogIn();
                    break;
                case R.id.btn_join:
                    intent = new Intent(LoginActivity.this, StudentJoinActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_find_pw:
                    intent = new Intent(LoginActivity.this, StudentFindPasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_join_manager:
                    intent = new Intent(LoginActivity.this, ManagerJoinActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };



    public void LogIn() {

    }




    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");

        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert_ex.setTitle("예제어플 알림!");
        AlertDialog alert = alert_ex.create();
        alert.show();

    }


}