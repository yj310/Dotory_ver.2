package com.mirim.dotory.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.R;

public class ManagerStayOutActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_stay_out);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_stayout).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerBoardActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerEnterActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerPointActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerMyActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_goout:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_stayout:
                    intent = new Intent(ManagerStayOutActivity.this, ManagerStayOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;

            }
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("????????? ?????????????????????????");

        alert_ex.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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