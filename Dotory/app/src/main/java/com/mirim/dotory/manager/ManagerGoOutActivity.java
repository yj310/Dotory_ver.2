package com.mirim.dotory.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.goout.GoOutInfoCustomAdapter;
import com.mirim.dotory.manager.goout.GoOutSettingActivity;
import com.mirim.dotory.student.StudentBoardActivity;
import com.mirim.dotory.student.StudentGoOutActivity;
import com.mirim.dotory.student.StudentPostCustomAdapter;
import com.mirim.dotory.student.StudentUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ManagerGoOutActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GoOutInfo> arrayList;

    private TextView txt_today;
    private TextView txt_week;
    private TextView txt_start_time;
    private TextView txt_end_time;

    private String id;
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_go_out);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        database = FirebaseDatabase.getInstance();

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_titlebar_stayout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_setting).setOnClickListener(onClickListener);

        txt_today = findViewById(R.id.txt_today);
        txt_week = findViewById(R.id.txt_week);
        txt_start_time = findViewById(R.id.txt_start_time);
        txt_end_time = findViewById(R.id.txt_end_time);


        Date now;
        now = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        txt_today.setText(formatter.format(now));

        formatter = new SimpleDateFormat("E");
        txt_week.setText(formatter.format(now) + "요일");

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        today = formatter.format(now);

        loadGoOutInfo();
        setRecyclerView();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerBoardActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerEnterActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerPointActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerMyActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_goout:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_titlebar_stayout:
                    intent = new Intent(ManagerGoOutActivity.this, ManagerStayOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_setting:
                    intent = new Intent(ManagerGoOutActivity.this, GoOutSettingActivity.class);
                    startActivity(intent);
                    break;



            }
        }
    };

    private void loadGoOutTime() {

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference_attendanceGoOut = database.getReference("GoOut");
        DatabaseReference databaseReference_studentGoOut = database.getReference("GoOut");
        DatabaseReference databaseReference_GoOutState = database.getReference("GoOut");

        DatabaseReference databaseReference = database.getReference("GoOut");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳

                if(!(dataSnapshot.child(today).child("time").child("start").getValue() == null)) {
                    // 외출 가능 시간 설정 있음
                    txt_start_time.setText(dataSnapshot.child(today).child("time").child("start").getValue().toString());
                    txt_end_time.setText(dataSnapshot.child(today).child("time").child("end").getValue().toString());
                } else {
                    // 외출 가능 시간 설정 없음
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
/*
        // 외출 정보 불러오기
        databaseReference_attendanceGoOut.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String date = dataSnapshot.child("date").getValue().toString();

                if(!date.equals(today))
                {
                    databaseReference_attendanceGoOut.child("date").setValue(today);
                    databaseReference_attendanceGoOut.child("time").child("first").setValue("00:00");
                    databaseReference_attendanceGoOut.child("time").child("second").setValue("00:00");
                    first_time = "00:00";
                    second_time = "00:00";
                    first_time_hour.setText("00");
                    first_time_minute.setText("00");
                    second_time_hour.setText("00");
                    second_time_minute.setText("00");

                    databaseReference_GoOutState.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            databaseReference_GoOutState.setValue("before");
                        };
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // DB를 가져오던 중 에러 발생 시
                            Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    first_time = dataSnapshot.child("time").child("first").getValue().toString();
                    second_time = dataSnapshot.child("time").child("second").getValue().toString();
                    first_time_hour.setText(first_time.split(":")[0]);
                    first_time_minute.setText(first_time.split(":")[1]);
                    second_time_hour.setText(second_time.split(":")[0]);
                    second_time_minute.setText(second_time.split(":")[1]);
                }

                if(!(first_time.equals("00:00")&&second_time.equals("00:00")))
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                    String now_time = formatter.format(now);

                    // 현재 시간이 외출 시간에 포함이 된다면
                    if((Integer.parseInt(first_time.split(":")[0]) < Integer.parseInt(now_time.split(":")[0])
                            || (Integer.parseInt(first_time.split(":")[0]) == Integer.parseInt(now_time.split(":")[0])
                            && Integer.parseInt(first_time.split(":")[1]) <= Integer.parseInt(now_time.split(":")[1])))

                            && (Integer.parseInt(second_time.split(":")[0]) > Integer.parseInt(now_time.split(":")[0])
                            || (Integer.parseInt(second_time.split(":")[0]) == Integer.parseInt(now_time.split(":")[0])
                            && Integer.parseInt(second_time.split(":")[1]) >= Integer.parseInt(now_time.split(":")[1])))) {

                        databaseReference_GoOutState.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue().toString().equals("before")) {
                                    databaseReference_GoOutState.setValue("ing");
                                }
                            };
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    // 외출 가능 시간이 지났다면
                    else if(Integer.parseInt(second_time.split(":")[0]) < Integer.parseInt(now_time.split(":")[0])
                            || (Integer.parseInt(second_time.split(":")[0]) == Integer.parseInt(now_time.split(":")[0])
                            && Integer.parseInt(second_time.split(":")[1]) < Integer.parseInt(now_time.split(":")[1])))
                    {
                        databaseReference_GoOutState.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.getValue().toString().equals("after")) {
                                    databaseReference_GoOutState.setValue("after");
                                }
                            };
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        databaseReference_studentGoOut.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    studentGoOutState = snapshot.getValue(StudentGoOutState.class);
                                    if(studentGoOutState.getState().equals("외출중"))
                                    {
                                        studentGoOutState.setState("외출중(지각)");
                                    }
                                    databaseReference_studentGoOut.child(snapshot.getKey()).setValue(studentGoOutState);

                                }
                            };

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void loadGoOutInfo() {

        DatabaseReference databaseReference;

        // 외출 정보 불러오기
        databaseReference = database.getReference("GoOut");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(today).child("time").child("start").getValue() != null) {
                    // 외출 가능 시간 설정 있음
                    txt_start_time.setText(dataSnapshot.child(today).child("time").child("start").getValue().toString());
                    txt_end_time.setText(dataSnapshot.child(today).child("time").child("end").getValue().toString());

                    Date now = new Date();
                    SimpleDateFormat formatter;
                    formatter = new SimpleDateFormat("HH");
                    int now_hour = Integer.parseInt(formatter.format(now));
                    formatter = new SimpleDateFormat("mm");
                    int now_minute = Integer.parseInt(formatter.format(now));

                    int end_hour = Integer.parseInt(dataSnapshot.child(today).child("time").child("end").getValue().toString().split(":")[0]);
                    int end_minute = Integer.parseInt(dataSnapshot.child(today).child("time").child("end").getValue().toString().split(":")[1]);

                    if((now_hour > end_hour) || ((now_hour == end_hour) && (now_minute > end_minute))) {
                        // 외출시간 초과
                        DatabaseReference databaseReference = database.getReference("GoOut/" + today + "/student");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // 파이어베이스의 데이터를 받아오는 곳
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    GoOutInfo goOutInfo = snapshot.getValue(GoOutInfo.class);
                                    if(goOutInfo.getState().equals("외출중")) {
                                        databaseReference.child(snapshot.getKey()).child("state").setValue("외출중(지각)");
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                } else {
                    // 외출 가능 시간 설정 없음
                    String use = dataSnapshot.child("timeList").child("use").getValue().toString();
                    if(!use.equals("0")) {
                        // 외출 시간 예약 있음
                        String start_time = dataSnapshot.child("timeList").child(use).child("start").getValue().toString();
                        String end_time = dataSnapshot.child("timeList").child(use).child("end").getValue().toString();

                        DatabaseReference databaseReference = database.getReference("GoOut");
                        databaseReference.child(today).child("time").child("start").setValue(start_time);
                        databaseReference.child(today).child("time").child("end").setValue(end_time);

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GoOut/" + today + "/student");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GoOutInfo goOutInfo = snapshot.getValue(GoOutInfo.class);
                    arrayList.add(goOutInfo);
                }
                //Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerGoOutActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new GoOutInfoCustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
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