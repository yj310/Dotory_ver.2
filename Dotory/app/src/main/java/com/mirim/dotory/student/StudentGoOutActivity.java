package com.mirim.dotory.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentGoOutActivity extends AppCompatActivity {

    TextView txt_hour;
    TextView txt_minute;
    TextView txt_AMPM;

    TextView txt_now_time;
    TextView txt_now_ampm;
    TextView txt_today;
    TextView txt_state;
    EditText input_reason;
    EditText input_place;
    TextView btn_submit;

    LinearLayout btn_time_select;

    private String email;
    private String key;
    private String today;
    private int room;
    private String name;
    
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_go_out);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_stayout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_submit).setOnClickListener(onClickListener);

        btn_time_select = findViewById(R.id.btn_time_select);
        btn_time_select.setOnClickListener(onClickListener);

        txt_hour = findViewById(R.id.text_select_hour);
        txt_minute = findViewById(R.id.text_select_minute);
        txt_AMPM = findViewById(R.id.text_select_AMPM);

        txt_now_time = findViewById(R.id.txt_now_time);
        txt_now_ampm = findViewById(R.id.txt_now_ampm);
        txt_today = findViewById(R.id.txt_today);
        txt_state = findViewById(R.id.txt_state);

        input_reason = findViewById(R.id.input_reason);
        input_place = findViewById(R.id.input_place);


        final Date now = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("MM월 dd일 E요일");
        txt_today.setText(formatter.format(now));

        formatter = new SimpleDateFormat("hh:mm");
        txt_now_time.setText(formatter.format(now));


        formatter = new SimpleDateFormat("a");
        if(formatter.format(now).equals("오전")) {
            txt_now_ampm.setText("AM");
            txt_AMPM.setText("AM");
        } else {
            txt_now_ampm.setText("PM");
            txt_AMPM.setText("PM");
        }

        formatter = new SimpleDateFormat("hh");
        txt_hour.setText(formatter.format(now));
        formatter = new SimpleDateFormat("mm");
        txt_minute.setText(formatter.format(now));

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        today = formatter.format(now);

        getState();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(StudentGoOutActivity.this, StudentBoardActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(StudentGoOutActivity.this, StudentGoOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(StudentGoOutActivity.this, StudentEnterActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(StudentGoOutActivity.this, StudentPointActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(StudentGoOutActivity.this, StudentMyActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_goout:
                    intent = new Intent(StudentGoOutActivity.this, StudentGoOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_stayout:
                    intent = new Intent(StudentGoOutActivity.this, StudentStayOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_time_select:

                    int hour = getIntInText(txt_hour);
                    int minute = getIntInText(txt_minute);
                    if(hour == 12) {
                        hour = 0;
                    }
                    if(txt_AMPM.getText().equals("PM")) {
                        hour += 12;
                    }

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            StudentGoOutActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,  new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                            // AMPM
                            if(hour < 12) {
                                txt_AMPM.setText("AM");
                            } else {
                                txt_AMPM.setText("PM");
                            }

                            // hour
                            int result_hour = hour % 12;
                            if(result_hour == 0) result_hour = 12;
                            txt_hour.setText(String.valueOf(result_hour));

                            // minute
                            if(minute < 10) {
                                txt_minute.setText("0" + String.valueOf(minute));
                            } else {
                                txt_minute.setText(String.valueOf(minute));
                            }
                        }
                    }, hour, minute, false);
                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    timePickerDialog.show();
                    break;
                case R.id.btn_submit:
                    addGoOutInfo();
                    break;

            }
        }
    };

    private void getState() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference;


        // 외출 시간 불러오기
        databaseReference = database.getReference("GoOut");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                if(!dataSnapshot.child(today).child("time").child("start").getValue().toString().equals("")) {
                    // 외박 가능 시간 설정 있음
                    startTime = dataSnapshot.child(today).child("time").child("start").getValue().toString();
                    endTime = dataSnapshot.child(today).child("time").child("end").getValue().toString();
                    //text_go_out_time.setText(first_time + "      " + second_time);
                } else {
                    // 외박 가능 시간 설정 없음
                }
                


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(StudentGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = database.getReference("StudentUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentUser studentUser = snapshot.getValue(StudentUser.class);
                    if(studentUser.getEmail().equals(email)) {
                        name = studentUser.getName();
                        room = studentUser.getRoom();

                        key = studentUser.getRoom() + email.split("@")[0];

                        // get my state
                        DatabaseReference databaseReference = database.getReference("GoOut/"+ today +"/student");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if(snapshot.getKey().equals(key)) {
                                        // 이미 외출 정보가 있음
                                        GoOutInfo goOutInfo = snapshot.getValue(GoOutInfo.class);

                                        txt_state.setText(goOutInfo.getState());
                                        input_reason.setText(goOutInfo.getReason());
                                        input_place.setText(goOutInfo.getPlace());
                                        String expect_time = goOutInfo.getExpect_time();
                                        txt_hour.setText(expect_time.split(":")[0]);
                                        txt_minute.setText(expect_time.split(":")[1]);
                                        input_reason.setFocusable(false);
                                        input_reason.setClickable(false);
                                        input_place.setFocusable(false);
                                        input_place.setClickable(false);
                                        btn_time_select.setFocusable(false);
                                        btn_time_select.setClickable(false);

                                        return;
                                    }
                                }
                                // 외출 정보 없음
                                Date now = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                String now_time = formatter.format(now);

                                if((Integer.parseInt(first_time.split(":")[0]) < Integer.parseInt(now_time.split(":")[0])
                                        || (Integer.parseInt(first_time.split(":")[0]) == Integer.parseInt(now_time.split(":")[0])
                                        && Integer.parseInt(first_time.split(":")[1]) <= Integer.parseInt(now_time.split(":")[1])))
                                        && (Integer.parseInt(second_time.split(":")[0]) > Integer.parseInt(now_time.split(":")[0])
                                        || (Integer.parseInt(second_time.split(":")[0]) == Integer.parseInt(now_time.split(":")[0])
                                        && Integer.parseInt(second_time.split(":")[1]) <= Integer.parseInt(now_time.split(":")[1])))) {

                                    text_state.setText("외출 가능");
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(StudentAttendanceGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }

                }
            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(StudentAttendanceGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addGoOutInfo() {

    }

    public int getIntInText(TextView view) {
        return Integer.parseInt(view.getText().toString());
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