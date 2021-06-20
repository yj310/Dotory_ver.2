package com.mirim.dotory.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.mirim.dotory.R;

public class ManagerLoginActivity extends AppCompatActivity {

    private TextView tv_id;
    private TextView tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);

        tv_id = findViewById(R.id.input_id);
        tv_password = findViewById(R.id.input_password);
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


    private void LogIn() {
        if (tv_id.getText().length() > 0 && tv_password.getText().length() > 0 ) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();;
            DatabaseReference databaseReference = database.getReference("ManagerUser");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // 파이어베이스의 데이터를 받아오는 곳
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.getKey().equals(tv_id.getText().toString())) {
                            if(snapshot.child("password").getValue().equals(tv_password.getText().toString())) {
                                Intent intent = new Intent(ManagerLoginActivity.this, ManagerBoardActivity.class);
                                ActivityCompat.finishAffinity(ManagerLoginActivity.this);
                                startActivity(intent);
                                finish();
                                return;
                            } else {
                                Toast.makeText(ManagerLoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    Toast.makeText(ManagerLoginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();


                };
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // DB를 가져오던 중 에러 발생 시
                    Toast.makeText(ManagerLoginActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "입력되지 않은 항목이 존재합니다. ", Toast.LENGTH_SHORT).show();
        }
        
    }

}