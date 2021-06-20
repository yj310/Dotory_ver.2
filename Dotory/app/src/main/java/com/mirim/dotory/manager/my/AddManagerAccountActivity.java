package com.mirim.dotory.manager.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerUser;
import com.mirim.dotory.student.StudentJoinSecondActivity;
import com.mirim.dotory.student.StudentUser;

public class AddManagerAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_id;
    private TextView tv_password;
    private TextView tv_password_again;

    private String txt_name;
    private String txt_phone;
    private String txt_id;
    private String txt_password;
    private String txt_password_again;

    private String pretext_name;
    private String pretext_phone;
    private String pretext_id;
    private String pretext_password;
    private String pretext_password_again;

    private boolean name_success = false;
    private boolean phone_success = false;
    private boolean id_success = false;
    private boolean password_success = false;
    private boolean password_again_success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_add_manager_account);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_id_check).setOnClickListener(onClickListener);

        tv_name = findViewById(R.id.input_name);
        tv_phone = findViewById(R.id.input_phone);
        tv_id = findViewById(R.id.input_id);
        tv_password = findViewById(R.id.input_password);
        tv_password_again = findViewById(R.id.input_password_again);


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
        tv_id.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_id = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_id))
                    return;

                if(tv_id.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_id = tv_id.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    tv_id.setCompoundDrawablesWithIntrinsicBounds(0, 0,  0, 0);
                    id_success = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_password = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_password))
                    return;

                if(tv_password.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_password = tv_password.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_password.length() >= 8) {
                        tv_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        password_success = true;
                        return;
                    }
                    tv_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    password_success = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_password_again.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_password_again = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_password_again))
                    return;

                if(tv_password_again.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_password_again = tv_password_again.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_password_again.equals(txt_password)) {
                        tv_password_again.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                        password_again_success = true;
                        return;
                    }
                    tv_password_again.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    password_again_success = false;
                }
            }

            // 텍스트가 변경된 이후에 호출.
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                case R.id.btn_id_check:
                    checkId();
                    break;

            }
        }
    };

    private void checkId() {

        txt_id = tv_id.getText().toString();
        if(txt_id.length() >= 4) {
            // 아이디 중복검사
            FirebaseDatabase database = FirebaseDatabase.getInstance();;
            DatabaseReference databaseReference = database.getReference("ManagerUser");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // 파이어베이스의 데이터를 받아오는 곳
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(!(snapshot.getKey().equals("password") || snapshot.getKey().equals("origin"))) {

                            ManagerUser managerUser = snapshot.getValue(ManagerUser.class);
                            if(managerUser.getId().equals(txt_id)) {
                                Toast.makeText(AddManagerAccountActivity.this, "중복되는 아이디입니다. ", Toast.LENGTH_SHORT).show();
                                tv_id.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                                id_success = false;
                                return;
                            }

                        }
                    }

                    Toast.makeText(AddManagerAccountActivity.this, "사용 가능한 아이디입니다. ", Toast.LENGTH_SHORT).show();
                    tv_id.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                    id_success = true;

                };
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // DB를 가져오던 중 에러 발생 시
                    Toast.makeText(AddManagerAccountActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "아이디는 최소 5자 이상이어야합니다. ", Toast.LENGTH_SHORT).show();
        }

        
        
    }

    private void Join() {
        if(name_success) {
            if(phone_success) {
                if(id_success) {
                    if(password_success) {
                        if(password_again_success) {
                            addNewUser();
                        } else {
                            Toast.makeText(this, "비밀번호가 일치하지 않습니다. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "비밀번호를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "아이디를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "휴대전화 번호를 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "이름을 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewUser() {
        ManagerUser managerUser = new ManagerUser(txt_id, txt_name, txt_password, txt_phone);
        mDatabase.child("ManagerUser").child(txt_id).setValue(managerUser);
        finish();
        Toast.makeText(this, "계정이 추가되었습니다. ", Toast.LENGTH_SHORT).show();
    }
}