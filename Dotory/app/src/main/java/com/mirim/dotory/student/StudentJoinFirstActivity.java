package com.mirim.dotory.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mirim.dotory.R;

public class StudentJoinFirstActivity extends AppCompatActivity {

    private TextView tv_email;
    private TextView tv_password;
    private TextView tv_password_again;

    private TextView btn_send_email_check;

    String txt_email;
    String txt_password;
    String txt_password_again;

    String pretext_email;
    String pretext_password;
    String pretext_password_again;

    boolean email_success = false;
    boolean password_success = false;
    boolean password_again_success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_first);

        findViewById(R.id.btn_next).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);

        tv_email = findViewById(R.id.input_email);
        tv_password = findViewById(R.id.input_password);
        tv_password_again = findViewById(R.id.input_password_again);

        // btn_send_email_check = findViewById(R.id.btn_send_email_check);

        tv_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pretext_email = s.toString();
            }

            // 텍스트가 변경될때 마다 호출된다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(pretext_email))
                    return;

                if(tv_email.isFocusable() && !s.toString().equals("")) {
                    try{
                        txt_email = tv_email.getText().toString();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (txt_email.length() == 8 || txt_email.length() == 5) {
                        if((txt_email.charAt(0) == 's' || txt_email.charAt(0) == 'w' || txt_email.charAt(0) == 'd')
                            && (txt_email.substring(1, 5).matches("-?\\d+"))) {
                            tv_email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_complete, 0);
                            email_success = true;
                            return;
                        }
                    }
                    tv_email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.drawable_right_warning, 0);
                    email_success = false;
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
                case R.id.btn_next:
                    NextPage();
                    break;
                case R.id.btn_back:
                    finish();
                    break;

            }
        }
    };


    public void NextPage() {
        String password = tv_password.getText().toString();
        String email = tv_email.getText().toString();

        if(email_success) {
            if(password_success) {
                if(password_again_success) {
                    Intent intent = new Intent(StudentJoinFirstActivity.this, StudentJoinSecondActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "비밀번호를 확인해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "이메일을 확인해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
        }


    }

}