package com.mirim.dotory.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.student.StudentLoginActivity;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.my.ManageManagerAccountActivity;
import com.mirim.dotory.manager.my.ManageStudentAccountActivity;
import com.mirim.dotory.student.StudentMyActivity;

public class ManagerMyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my);


        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);

        findViewById(R.id.btn_manage_student_account).setOnClickListener(onClickListener);
        findViewById(R.id.btn_manage_manager_account).setOnClickListener(onClickListener);
        findViewById(R.id.btn_alert_setting).setOnClickListener(onClickListener);

        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(ManagerMyActivity.this, ManagerBoardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerMyActivity.this, ManagerGoOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerMyActivity.this, ManagerEnterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerMyActivity.this, ManagerPointActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerMyActivity.this, ManagerMyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_logout:
                    AlertDialog.Builder alert_ex = new AlertDialog.Builder(ManagerMyActivity.this);
                    alert_ex.setMessage("로그아웃하시겠습니까?");

                    alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert_ex.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ManagerMyActivity.this, StudentLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert_ex.setTitle("Dotory");
                    AlertDialog alert = alert_ex.create();
                    alert.show();

                    break;
                case R.id.btn_manage_student_account:
                    intent = new Intent(ManagerMyActivity.this, ManageStudentAccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_manage_manager_account:
                    intent = new Intent(ManagerMyActivity.this, ManageManagerAccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_alert_setting:
                    /*intent = new Intent(ManagerMyActivity.this, .class);
                    startActivity(intent);
                    finish();*/
                    break;

            }
        }
    };

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
        alert_ex.setTitle("Dotory");
        AlertDialog alert = alert_ex.create();
        alert.show();

    }

}