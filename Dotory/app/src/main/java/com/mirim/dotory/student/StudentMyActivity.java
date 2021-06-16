package com.mirim.dotory.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.LoginActivity;
import com.mirim.dotory.R;

public class StudentMyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my);


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
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(StudentMyActivity.this, StudentGoOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(StudentMyActivity.this, StudentEnterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(StudentMyActivity.this, StudentPointActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(StudentMyActivity.this, StudentMyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_my_bookmark:
                    intent = new Intent(StudentMyActivity.this, StudentMyBookmarkActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_my_list:
                    intent = new Intent(StudentMyActivity.this, StudentMyListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_my_rule:
                    intent = new Intent(StudentMyActivity.this, StudentMyRuleActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_logout:
                    intent = new Intent(StudentMyActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };
}