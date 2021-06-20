package com.mirim.dotory.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    public void LogIn() {/*
        final String password = ((EditText) findViewById(R.id.input_password)).getText().toString();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("ManagerUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳

                if(password.length() > 0) {
                    if (password.equals(dataSnapshot.child("password").getValue().toString())) {
                        Intent intent = new Intent(ManagerLoginActivity.this, ManagerBoardActivity.class);
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(intent);
                    } else {
                        Toast.makeText(ManagerLoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManagerLoginActivity.this, "비밀번호를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                }

            };



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerLoginActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        Intent intent = new Intent(ManagerLoginActivity.this, ManagerBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}