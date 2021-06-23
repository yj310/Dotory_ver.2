package com.mirim.dotory.manager.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.mirim.dotory.manager.ManagerUser;
import com.mirim.dotory.student.StudentUser;
import com.mirim.dotory.student.my.StudentMyInfoActivity;

public class ManagerMyInfoActivity extends AppCompatActivity {

    private String id;
    private TextView txt_name;
    private TextView txt_phone;
    private TextView txt_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_info);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_id = findViewById(R.id.txt_id);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_change_password).setOnClickListener(onClickListener);

        loadData();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_change_password:
                    // 비밀번호 변경 폼
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
                // 파이어베이스의 데이터를 받아오는 곳
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ManagerUser managerUser = snapshot.getValue(ManagerUser.class);
                    if(managerUser.getId().equals(id)) {
                        txt_name.setText(managerUser.getName());
                        txt_phone.setText(managerUser.getPhone());
                        txt_id.setText(managerUser.getId());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerMyInfoActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}