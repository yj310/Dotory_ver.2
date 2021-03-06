package com.mirim.dotory.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.EnterInfo;
import com.mirim.dotory.manager.my.ManagerMyInfoActivity;
import com.mirim.dotory.student.StudentLoginActivity;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.my.ManageManagerAccountActivity;
import com.mirim.dotory.manager.my.ManageStudentAccountActivity;
import com.mirim.dotory.student.StudentMyActivity;

public class ManagerMyActivity extends AppCompatActivity {

    private String id;
    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_name = findViewById(R.id.txt_name);

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);

        findViewById(R.id.btn_manage_student_account).setOnClickListener(onClickListener);
        findViewById(R.id.btn_manage_manager_account).setOnClickListener(onClickListener);

        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_info).setOnClickListener(onClickListener);

        loadData();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(ManagerMyActivity.this, ManagerBoardActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerMyActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerMyActivity.this, ManagerEnterActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerMyActivity.this, ManagerPointActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerMyActivity.this, ManagerMyActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_logout:
                    AlertDialog.Builder alert_ex = new AlertDialog.Builder(ManagerMyActivity.this);
                    alert_ex.setMessage("???????????????????????????????");

                    alert_ex.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert_ex.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
                case R.id.btn_info:
                    intent = new Intent(ManagerMyActivity.this, ManagerMyInfoActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    break;

            }
        }
    };

    private void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ManagerUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ????????????????????? ???????????? ???????????? ???
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ManagerUser managerUser = snapshot.getValue(ManagerUser.class);
                    if(managerUser.getId().equals(id)) {
                        txt_name.setText(managerUser.getName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB??? ???????????? ??? ?????? ?????? ???
                Toast.makeText(ManagerMyActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

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