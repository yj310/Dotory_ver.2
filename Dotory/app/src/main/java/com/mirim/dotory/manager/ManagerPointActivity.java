package com.mirim.dotory.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.R;

public class ManagerPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_point);


        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_pointstate).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(ManagerPointActivity.this, ManagerBoardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerPointActivity.this, ManagerGoOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerPointActivity.this, ManagerEnterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerPointActivity.this, ManagerPointActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerPointActivity.this, ManagerMyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_point:
                    intent = new Intent(ManagerPointActivity.this, ManagerPointActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_pointstate:
                    intent = new Intent(ManagerPointActivity.this, ManagerPointStateActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;

            }
        }
    };
}