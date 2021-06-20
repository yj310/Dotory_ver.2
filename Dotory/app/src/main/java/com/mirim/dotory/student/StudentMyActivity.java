package com.mirim.dotory.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.R;
import com.mirim.dotory.student.my.BookmarkActivity;
import com.mirim.dotory.student.my.ListActivity;
import com.mirim.dotory.student.my.RuleActivity;

public class StudentMyActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);

        findViewById(R.id.btn_my_bookmark).setOnClickListener(onClickListener);
        findViewById(R.id.btn_my_list).setOnClickListener(onClickListener);
        findViewById(R.id.btn_my_rule).setOnClickListener(onClickListener);
        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(StudentMyActivity.this, StudentBoardActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(StudentMyActivity.this, StudentGoOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(StudentMyActivity.this, StudentEnterActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(StudentMyActivity.this, StudentPointActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(StudentMyActivity.this, StudentMyActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_my_bookmark:
                    intent = new Intent(StudentMyActivity.this, BookmarkActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    break;
                case R.id.btn_my_list:
                    intent = new Intent(StudentMyActivity.this, ListActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    break;
                case R.id.btn_my_rule:
                    intent = new Intent(StudentMyActivity.this, RuleActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_logout:
                    AlertDialog.Builder alert_ex = new AlertDialog.Builder(StudentMyActivity.this);
                    alert_ex.setMessage("로그아웃하시겠습니까?");

                    alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert_ex.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(StudentMyActivity.this, StudentLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert_ex.setTitle("Dotory");
                    AlertDialog alert = alert_ex.create();
                    alert.show();

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