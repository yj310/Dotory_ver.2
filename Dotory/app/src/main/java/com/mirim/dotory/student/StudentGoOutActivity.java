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

import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentGoOutActivity extends AppCompatActivity {

    FirebaseDatabase database;

    TextView txt_hour;
    TextView txt_minute;
    TextView txt_AMPM;
    TextView txt_now_time;
    TextView txt_now_ampm;
    TextView txt_today;
    TextView txt_state;
    TextView btn_submit;
    TextView txt_start_time;
    TextView txt_start_ampm;
    TextView txt_start_end;
    TextView txt_end_time;
    TextView txt_end_ampm;
    EditText input_reason;
    EditText input_place;

    LinearLayout btn_time_select;

    private String email;
    private String key;
    private String today;
    private int room;
    private String name;
    
    private String startTime;
    private String endTime;

    private Date now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_go_out);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        database = FirebaseDatabase.getInstance();

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
        txt_start_time = findViewById(R.id.txt_start_time);
        txt_start_ampm = findViewById(R.id.txt_start_ampm);
        txt_start_end = findViewById(R.id.txt_start_end);
        txt_end_time = findViewById(R.id.txt_end_time);
        txt_end_ampm = findViewById(R.id.txt_end_ampm);
        txt_hour = findViewById(R.id.text_select_hour);
        txt_minute = findViewById(R.id.text_select_minute);
        txt_AMPM = findViewById(R.id.text_select_AMPM);
        txt_now_time = findViewById(R.id.txt_now_time);
        txt_now_ampm = findViewById(R.id.txt_now_ampm);
        txt_today = findViewById(R.id.txt_today);
        txt_state = findViewById(R.id.txt_state);
        input_reason = findViewById(R.id.input_reason);
        input_place = findViewById(R.id.input_place);


        now = new Date();

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

        loadGoOutInfo();

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
                    /*intent = new Intent(StudentGoOutActivity.this, StudentStayOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();*/
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

    private void loadGoOutInfo() {

        DatabaseReference databaseReference;


        // 외출 정보 불러오기
        databaseReference = database.getReference("GoOut");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                if(!(dataSnapshot.child(today).child("time").child("start").getValue() == null)) {
                    // 외출 가능 시간 설정 있음
                    startTime = dataSnapshot.child(today).child("time").child("start").getValue().toString();
                    endTime = dataSnapshot.child(today).child("time").child("end").getValue().toString();

                    txt_start_time.setText(getTime(startTime)[0]);
                    txt_start_ampm.setText(getTime(startTime)[1]);
                    txt_end_time.setText(getTime(endTime)[0]);
                    txt_end_ampm.setText(getTime(endTime)[1]);

                    DatabaseReference databaseReference = database.getReference("StudentUser");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // 파이어베이스의 데이터를 받아오는 곳
                            // dataSnapshot.child("key").getValue();
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

                                                    //txt_hour.setText(expect_time.split(":")[0]);
                                                    //txt_minute.setText(expect_time.split(":")[1]);

                                                    txt_hour.setText(getTime(expect_time)[0].split(":")[0]);
                                                    txt_minute.setText(getTime(expect_time)[0].split(":")[1]);
                                                    txt_AMPM.setText(getTime(expect_time)[1]);

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
                                            int start_hour = Integer.parseInt(startTime.split(":")[0]);
                                            int start_minute = Integer.parseInt(startTime.split(":")[1]);
                                            int end_hour = Integer.parseInt(endTime.split(":")[0]);
                                            int end_minute = Integer.parseInt(endTime.split(":")[1]);
                                            int now_hour;
                                            int now_minute;

                                            Date now = new Date();
                                            SimpleDateFormat formatter;
                                            formatter = new SimpleDateFormat("HH");
                                            now_hour = Integer.parseInt(formatter.format(now));
                                            formatter = new SimpleDateFormat("mm");
                                            now_minute = Integer.parseInt(formatter.format(now));


                                            // 현재 시간이 외출 시간 범위 내
                                            if((start_hour < now_hour || ((start_hour == now_hour) && (start_minute <= now_minute)))
                                                    && (end_hour > now_hour || ((end_hour == now_hour) && (end_minute >= now_minute)))) {
                                                txt_state.setText("외출 가능");
                                            } else {
                                                // 외출 불가능
                                                txt_state.setText("외출 불가능");
                                                input_reason.setFocusable(false);
                                                input_reason.setClickable(false);
                                                input_place.setFocusable(false);
                                                input_place.setClickable(false);
                                                btn_time_select.setFocusable(false);
                                                btn_time_select.setClickable(false);
                                            }

                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // DB를 가져오던 중 에러 발생 시
                                            Toast.makeText(StudentGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                }

                            }
                        };
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // DB를 가져오던 중 에러 발생 시
                            Toast.makeText(StudentGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // 외출 가능 시간 설정 없음
                    txt_start_time.setText(null);
                    txt_start_ampm.setText(null);
                    txt_end_time.setText(null);
                    txt_end_ampm.setText(null);

                    txt_start_end.setText("외출 가능 시간이\n 설정되지 않았습니다.");

                    input_reason.setFocusable(false);
                    input_reason.setClickable(false);
                    input_place.setFocusable(false);
                    input_place.setClickable(false);
                    btn_time_select.setFocusable(false);
                    btn_time_select.setClickable(false);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(StudentGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addGoOutInfo() {
        if(txt_state.getText().equals("외출 가능")) {
            String reason = input_reason.getText().toString();
            String place = input_place.getText().toString();
            if(reason.length() > 0) {
                if(place.length() > 0) {
                    String expect_time = getTime(txt_hour.getText().toString() + ":" + txt_minute.getText().toString(), txt_AMPM.getText().toString());
                    String go_out_time = getTime(txt_now_time.getText().toString(), txt_now_ampm.getText().toString());

                    GoOutInfo goOutInfo = new GoOutInfo(expect_time, go_out_time, name, place, reason, room);


                    DatabaseReference databaseReference = database.getReference("GoOut/" + today + "/student");
                    databaseReference.child(key).setValue(goOutInfo);
                    Toast.makeText(this, "외출 신청이 완료되었습니다. ", Toast.LENGTH_SHORT).show();

                    // 리로드
                    Intent intent = getIntent();
                    intent.putExtra("email", email);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                } else {
                    Toast.makeText(this, "외출 장소를 입력해주시기 바랍니다. ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "외출 사유를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 13:00 -> {01:00, pm}
    private String[] getTime(String time) {

        final int hour = Integer.parseInt(time.split(":")[0]);
        final int minute = Integer.parseInt(time.split(":")[1]);

        String ampm;
        String result_minute;
        int result_hour;

        String[] returnString = new String[2];

        if(hour < 12) {
            ampm = "AM";
        } else {
            ampm = "PM";
        }

        // hour
        result_hour = hour % 12;
        if(result_hour == 0) result_hour = 12;

        // minute
        if(minute < 10) {
            result_minute = "0" + String.valueOf(minute);
        } else {
            result_minute = String.valueOf(minute);
        }


        returnString[0] = String.valueOf(result_hour) + ":" + result_minute;
        returnString[1] = ampm;

        return returnString;

    }

    // (01:00, pm) -> 13:00
    private String getTime(String time, String ampm) {

        final int hour = Integer.parseInt(time.split(":")[0]);
        final int minute = Integer.parseInt(time.split(":")[1]);

        String result_minute;
        int result_hour = hour;

        String returnString;

        if (hour == 12) {
            result_hour = 0;
        }
        if (ampm.equals("PM")) {
            result_hour += 12;
        }

        // minute
        if(minute < 10) {
            result_minute = "0" + String.valueOf(minute);
        } else {
            result_minute = String.valueOf(minute);
        }

        returnString = String.valueOf(result_hour) + ":" + result_minute;

        return returnString;
    }

    // TextView -> int
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