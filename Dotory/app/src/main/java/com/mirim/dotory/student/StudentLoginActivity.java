package com.mirim.dotory.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerLoginActivity;

public class StudentLoginActivity extends AppCompatActivity {

    private TextView tv_email;
    private TextView tv_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_join).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login_manager).setOnClickListener(onClickListener);

        tv_email = findViewById(R.id.input_email);
        tv_password = findViewById(R.id.input_password);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_login:
                    LogIn();
                    break;
                case R.id.btn_join:
                    intent = new Intent(StudentLoginActivity.this, StudentJoinFirstActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_find_pw:
                    intent = new Intent(StudentLoginActivity.this, StudentFindPasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login_manager:
                    intent = new Intent(StudentLoginActivity.this, ManagerLoginActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };



    public void LogIn() {

        final String email = tv_email.getText().toString();
        final String password = tv_password.getText().toString();

        if (email.length() > 0 && password.length() > 0 ) {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(StudentLoginActivity.this, StudentBoardActivity.class);
                            intent.putExtra("email", email.split("@")[0]);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(StudentLoginActivity.this, "로그인에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(StudentLoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }

                    }


                });


        } else {
            Toast.makeText(StudentLoginActivity.this, "입력되지 않은 항목이 존재합니다.", Toast.LENGTH_SHORT).show();

        }
    }




    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");

        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
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