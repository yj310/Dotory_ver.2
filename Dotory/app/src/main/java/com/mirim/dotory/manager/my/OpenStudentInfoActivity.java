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
import com.mirim.dotory.student.StudentUser;
import com.mirim.dotory.student.my.StudentMyInfoActivity;

public class OpenStudentInfoActivity extends AppCompatActivity {

    private String email;
    private TextView txt_name;
    private TextView txt_room;
    private TextView txt_email;
    private TextView txt_phone;
    private TextView txt_guardian;
    private TextView txt_school_info;
    private TextView txt_dirth;
    private TextView txt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_student_info);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        txt_name = findViewById(R.id.txt_name);
        txt_room = findViewById(R.id.txt_room);
        txt_email = findViewById(R.id.txt_email);
        txt_phone = findViewById(R.id.txt_phone);
        txt_guardian = findViewById(R.id.txt_guardian);
        txt_school_info = findViewById(R.id.txt_school_info);
        txt_dirth = findViewById(R.id.txt_dirth);
        txt_address = findViewById(R.id.txt_address);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);

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

            }
        }
    };


    private void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("StudentUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentUser studentUser = snapshot.getValue(StudentUser.class);
                    if(studentUser.getEmail().equals(email)) {

                        txt_name.setText(studentUser.getName());
                        txt_room.setText(studentUser.getRoom() + "호");
                        txt_email.setText(studentUser.getEmail());
                        txt_phone.setText(studentUser.getPhone());
                        txt_guardian.setText(studentUser.getGuardian_phone());
                        txt_school_info.setText(studentUser.getGrade() + "학년 " + studentUser.getSchool_class() + "반" + studentUser.getClass_number() + "번");
                        txt_dirth.setText(studentUser.getBirth_year() + "년 " + studentUser.getBirth_month() + "월 " + studentUser.getBirth_day() + "일");
                        txt_address.setText(studentUser.getAddress_load() + "\n" + studentUser.getAddress_detail());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(OpenStudentInfoActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}