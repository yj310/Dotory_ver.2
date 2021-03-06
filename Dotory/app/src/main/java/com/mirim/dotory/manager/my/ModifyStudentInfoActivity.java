package com.mirim.dotory.manager.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.post.ModifyPostActivity;
import com.mirim.dotory.student.StudentUser;

public class ModifyStudentInfoActivity extends AppCompatActivity {

    private String email;

    private EditText input_name;
    private EditText input_room;
    private EditText input_phone;
    private EditText input_guardian;
    private EditText input_address_load;
    private EditText input_address_detail;
    private Spinner spinner_grade;
    private Spinner spinner_class;
    private Spinner spinner_class_number;
    private Spinner spinner_birth_year;
    private Spinner spinner_birth_month;
    private Spinner spinner_birth_day;

    private StudentUser studentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_modify_student_info);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        input_name = findViewById(R.id.input_name);
        input_room = findViewById(R.id.input_room);
        input_phone = findViewById(R.id.input_phone);
        input_guardian = findViewById(R.id.input_guardian);
        input_address_load = findViewById(R.id.input_address_load);
        input_address_detail = findViewById(R.id.input_address_detail);
        spinner_grade = findViewById(R.id.spinner_grade);
        spinner_class = findViewById(R.id.spinner_class);
        spinner_class_number = findViewById(R.id.spinner_class_number);
        spinner_birth_year = findViewById(R.id.spinner_birth_year);
        spinner_birth_month = findViewById(R.id.spinner_birth_month);
        spinner_birth_day = findViewById(R.id.spinner_birth_day);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_submit).setOnClickListener(onClickListener);

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
                case R.id.btn_submit:
                    chackPassword();
                    break;

            }
        }
    };

    private void chackPassword() {

        AlertDialog.Builder alert_ex = new AlertDialog.Builder(ModifyStudentInfoActivity.this);
        alert_ex.setMessage("?????????????????????????");

        alert_ex.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder alert_ex2 = new AlertDialog.Builder(ModifyStudentInfoActivity.this);
                alert_ex2.setMessage("??????????????? ???????????????.");

                alert_ex2.setView(R.layout.alert_dialog);

                alert_ex2.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert_ex2.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("ManagerUser");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // ????????????????????? ???????????? ???????????? ???
                                Dialog dial = (Dialog) dialog;
                                EditText input = (EditText) dial.findViewById(R.id.addboxdialog);
                                if(dataSnapshot.child("password").getValue().toString().equals(input.getText().toString()) ) {
                                    // ????????? ??????
                                    modifyInfo();
                                    Toast.makeText(ModifyStudentInfoActivity.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                    reloadPage();
                                } else {
                                    Toast.makeText(ModifyStudentInfoActivity.this, "??????????????? ?????? ????????????. ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB??? ???????????? ??? ?????? ?????? ???
                                Toast.makeText(ModifyStudentInfoActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                AlertDialog alert2 = alert_ex2.create();
                alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert2.show();

            }
        });

        AlertDialog alert = alert_ex.create();
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.show();

    }

    private void modifyInfo() {

        getData();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("StudentUser/" + email);
        databaseReference.setValue(studentUser);


    }

    private void getData() {
        studentUser = new StudentUser(
                input_name.getText().toString(),
                Integer.parseInt(input_room.getText().toString()),
                email,
                input_phone.getText().toString(),
                input_guardian.getText().toString(),
                input_address_load.getText().toString(),
                input_address_detail.getText().toString(),
                Integer.parseInt(spinner_grade.getSelectedItem().toString()),
                Integer.parseInt(spinner_class.getSelectedItem().toString()),
                Integer.parseInt(spinner_class_number.getSelectedItem().toString()),
                Integer.parseInt(spinner_birth_year.getSelectedItem().toString()),
                Integer.parseInt(spinner_birth_month.getSelectedItem().toString()),
                Integer.parseInt(spinner_birth_day.getSelectedItem().toString())
        );
    }

    private void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("StudentUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ????????????????????? ???????????? ???????????? ???
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentUser studentUser = snapshot.getValue(StudentUser.class);
                    if(studentUser.getEmail().equals(email)) {
                        input_name.setText(studentUser.getName());
                        input_room.setText(String.valueOf(studentUser.getRoom()));
                        input_phone.setText(studentUser.getPhone());
                        input_guardian.setText(studentUser.getGuardian_phone());
                        input_address_load.setText(studentUser.getAddress_load());
                        input_address_detail.setText(studentUser.getAddress_detail());

                        String[] list;
                        list = getResources().getStringArray(R.array.array_grade);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getGrade()+"")){
                                spinner_grade.setSelection(i);
                                break;
                            }
                        }

                        list = getResources().getStringArray(R.array.array_class);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getSchool_class()+"")){
                                spinner_class.setSelection(i);
                                break;
                            }
                        }

                        list = getResources().getStringArray(R.array.array_class_number);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getClass_number()+"")){
                                spinner_class_number.setSelection(i);
                                break;
                            }
                        }

                        list = getResources().getStringArray(R.array.array_birth_year);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getBirth_year()+"")){
                                spinner_birth_year.setSelection(i);
                                break;
                            }
                        }

                        list = getResources().getStringArray(R.array.array_birth_month);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getBirth_month()+"")){
                                spinner_birth_month.setSelection(i);
                                break;
                            }
                        }

                        list = getResources().getStringArray(R.array.array_birth_day_31);
                        for (int i = 0; i < list.length; i++) {
                            if(list[i].equals(studentUser.getBirth_day()+"")){
                                spinner_birth_day.setSelection(i);
                                break;
                            }
                        }

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB??? ???????????? ??? ?????? ?????? ???
                Toast.makeText(ModifyStudentInfoActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reloadPage() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}