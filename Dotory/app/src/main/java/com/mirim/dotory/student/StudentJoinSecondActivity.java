package com.mirim.dotory.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirim.dotory.R;

public class StudentJoinSecondActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String email;
    private String password;

    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_guardian_phone;
    private TextView tv_address_load;
    private TextView tv_address_detail;
    private Spinner spinner_birth_year;
    private Spinner spinner_birth_month;
    private Spinner spinner_birth_day;
    private Spinner spinner_grade;
    private Spinner spinner_school_class;
    private Spinner spinner_class_number;

    private String txt_name;
    private String txt_phone;
    private String txt_guardian_phone;
    private String txt_address_load;
    private String txt_address_detail;
    private int txt_birth_year;
    private int txt_birth_month;
    private int txt_birth_day;
    private int txt_grade;
    private int txt_school_class;
    private int txt_class_number;

    private String pretext_name;
    private String pretext_phone;
    private String pretext_guardian_phone;
    private String pretext_address_load;
    private String pretext_address_detail;
    private int pretext_birth_year;
    private int pretext_birth_month;
    private int pretext_birth_day;
    private int pretext_grade;
    private int pretext_school_class;
    private int pretext_class_number;

    private boolean name_success = false;
    private boolean phone_success = false;
    private boolean guardian_phone_success = false;
    private boolean address_load_success = false;
    private boolean address_detail_success = false;
    private boolean birth_year_success = false;
    private boolean birth_month_success = false;
    private boolean birth_day_success = false;
    private boolean grade_success = false;
    private boolean school_class_success = false;
    private boolean class_number_success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_second);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);

        tv_name = findViewById(R.id.input_name);
        tv_phone = findViewById(R.id.input_phone);
        tv_guardian_phone = findViewById(R.id.input_guardian_phone);
        tv_address_load = findViewById(R.id.input_address_load);
        tv_address_detail = findViewById(R.id.input_address_detail);

        spinner_grade = findViewById(R.id.spinner_grade);
        spinner_school_class = findViewById(R.id.spinner_class);
        spinner_class_number = findViewById(R.id.spinner_class_number);
        spinner_birth_year = findViewById(R.id.spinner_birth_year);
        spinner_birth_month = findViewById(R.id.spinner_birth_month);
        spinner_birth_day = findViewById(R.id.spinner_birth_day);


        tv_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_name = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_name))
                    return;

                if(tv_name.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_name = tv_name.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_name.length() >= 2) {
                        tv_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        name_success = true;
                        return;
                    }
                    tv_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    name_success = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_phone = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_phone))
                    return;

                if(tv_phone.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_phone = tv_phone.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_phone.length() >= 13) {
                        tv_phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        phone_success  = true;
                        return;
                    }
                    tv_phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    phone_success  = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_guardian_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_guardian_phone = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_guardian_phone))
                    return;

                if(tv_guardian_phone.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_guardian_phone = tv_guardian_phone.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_guardian_phone.length()>= 13) {
                        tv_guardian_phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        guardian_phone_success  = true;
                        return;
                    }
                    tv_guardian_phone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    guardian_phone_success  = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override public void afterTextChanged(Editable s) {

            }
        });
        tv_address_load.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_address_load = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_address_load))
                    return;

                if(tv_address_load.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_address_load = tv_address_load.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_address_load.length() >= 1) {
                        tv_address_load.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        address_load_success  = true;
                        return;
                    }
                    tv_address_load.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    address_load_success  = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_address_detail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_address_detail = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_address_detail))
                    return;

                if(tv_address_detail.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_address_detail = tv_address_detail.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_address_detail.length() >= 1) {
                        tv_address_detail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        address_detail_success  = true;
                        return;
                    }
                    tv_address_detail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    address_detail_success  = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 아이템이 선택될때 마다 호출된다.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_join:
                    Join();
                    break;
                case R.id.btn_back:
                    finish();
                    break;

            }
        }
    };


    public void Join() {

        if(name_success) {
            if(phone_success) {
                if(guardian_phone_success) {
                    if(address_load_success) {
                        if(address_detail_success) {

                            mAuth.createUserWithEmailAndPassword(email + "@e-mirim.hs.kr", password)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                addNewUser();
                                            } else {
                                                if (task.getException() != null) {
                                                    Toast.makeText(StudentJoinSecondActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "상세주소를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "도로명주소를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "보호자 연락처를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "휴대전화 번호를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "이름을 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
        }

    }


    private void addNewUser() {

        String name = tv_name.getText().toString();
        String phone = tv_phone.getText().toString();
        String guardian_phone = tv_guardian_phone.getText().toString();
        String address_load = tv_address_load.getText().toString();
        String address_detail = tv_address_detail.getText().toString();
        int grade = Integer.parseInt((String) spinner_grade.getSelectedItem());
        int school_class = Integer.parseInt((String) spinner_school_class.getSelectedItem());
        int class_number = Integer.parseInt((String) spinner_class_number.getSelectedItem());
        int birth_year = Integer.parseInt((String) spinner_birth_year.getSelectedItem());
        int birth_month = Integer.parseInt((String) spinner_birth_month.getSelectedItem());
        int birth_day = Integer.parseInt((String) spinner_birth_day.getSelectedItem());

        StudentUser studentUser = new StudentUser(name, 0, email, phone, guardian_phone, address_load, address_detail, grade, school_class, class_number, birth_year, birth_month, birth_day);

        mDatabase.child("StudentUser").child(email).setValue(studentUser);
        Toast.makeText(StudentJoinSecondActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(StudentJoinSecondActivity.this, StudentLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}