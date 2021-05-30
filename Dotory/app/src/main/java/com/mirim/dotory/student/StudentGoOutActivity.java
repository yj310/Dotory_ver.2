package com.mirim.dotory.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mirim.dotory.R;

public class StudentGoOutActivity extends AppCompatActivity {

    TextView text_hour;
    TextView text_minute;
    TextView text_AMPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_go_out);


        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_stayout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_time_select).setOnClickListener(onClickListener);

        text_hour = findViewById(R.id.text_select_hour);
        text_minute = findViewById(R.id.text_select_minute);
        text_AMPM = findViewById(R.id.text_select_AMPM);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(StudentGoOutActivity.this, StudentBoardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(StudentGoOutActivity.this, StudentGoOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(StudentGoOutActivity.this, StudentEnterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(StudentGoOutActivity.this, StudentPointActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(StudentGoOutActivity.this, StudentMyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_goout:
                    intent = new Intent(StudentGoOutActivity.this, StudentGoOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_stayout:
                    intent = new Intent(StudentGoOutActivity.this, StudentStayOutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_time_select:

                    int hour = getIntInText(text_hour);
                    int minute = getIntInText(text_minute);
                    if(hour == 12) {
                        hour = 0;
                    }
                    if(text_AMPM.getText().equals("PM")) {
                        hour += 12;
                    }

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            StudentGoOutActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,  new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                            // AMPM
                            if(hour < 12) {
                                text_AMPM.setText("AM");
                            } else {
                                text_AMPM.setText("PM");
                            }

                            // hour
                            int result_hour = hour % 12;
                            if(result_hour == 0) result_hour = 12;
                            text_hour.setText(String.valueOf(result_hour));

                            // minute
                            if(minute < 10) {
                                text_minute.setText("0" + String.valueOf(minute));
                            } else {
                                text_minute.setText(String.valueOf(minute));
                            }
                        }
                    }, hour, minute, false);
                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    timePickerDialog.show();
                    break;

            }
        }
    };

    public int getIntInText(TextView view) {
        return Integer.parseInt(view.getText().toString());
    }


}