package com.mirim.dotory.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mirim.dotory.EnterInfo;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.MainActivity;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.enter.EnterInfoCustomAdapter;
import com.mirim.dotory.manager.enter.EnterSettingActivity;
import com.mirim.dotory.manager.goout.GoOutInfoCustomAdapter;
import com.mirim.dotory.manager.goout.GoOutSettingActivity;
import com.mirim.dotory.student.StudentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagerEnterActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EnterInfo> arrayList;
    
    private IntentIntegrator qrScan;

    private TextView txt_today;
    private TextView txt_week;
    private TextView txt_start_time;
    private TextView txt_end_time;

    private String id;
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_enter);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        database = FirebaseDatabase.getInstance();

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);
        findViewById(R.id.btn_setting).setOnClickListener(onClickListener);
        findViewById(R.id.btn_scan).setOnClickListener(onClickListener);

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
                    intent = new Intent(ManagerEnterActivity.this, ManagerBoardActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(ManagerEnterActivity.this, ManagerGoOutActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(ManagerEnterActivity.this, ManagerEnterActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(ManagerEnterActivity.this, ManagerPointActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(ManagerEnterActivity.this, ManagerMyActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_setting:
                    intent = new Intent(ManagerEnterActivity.this, EnterSettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_scan:
                    createQRScanner();
                    break;

            }
        }
    };


    private void loadGoOutInfo() {

        DatabaseReference databaseReference;

        // 외출 정보 불러오기
        databaseReference = database.getReference("Enter");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(today).child("time").child("start").getValue() != null) {
                    // 외출 가능 시간 설정 있음
                    txt_start_time.setText(dataSnapshot.child(today).child("time").child("start").getValue().toString());
                    txt_end_time.setText(dataSnapshot.child(today).child("time").child("end").getValue().toString());
                    if(dataSnapshot.child(today).child("state").getValue().toString().equals("before")) {

                        String start_time = dataSnapshot.child(today).child("time").child("start").getValue().toString();
                        String end_time = dataSnapshot.child(today).child("time").child("end").getValue().toString();

                        int start_hour = Integer.parseInt(start_time.split(":")[0]);
                        int start_minute = Integer.parseInt(start_time.split(":")[1]);
                        int end_hour = Integer.parseInt(end_time.split(":")[0]);
                        int end_minute = Integer.parseInt(end_time.split(":")[1]);
                        int now_hour;
                        int now_minute;

                        Date now = new Date();
                        SimpleDateFormat formatter;
                        formatter = new SimpleDateFormat("HH");
                        now_hour = Integer.parseInt(formatter.format(now));
                        formatter = new SimpleDateFormat("mm");
                        now_minute = Integer.parseInt(formatter.format(now));


                        // 현재 시간이 입소 시간 범위 내
                        if((start_hour < now_hour || ((start_hour == now_hour) && (start_minute <= now_minute)))
                                && (end_hour > now_hour || ((end_hour == now_hour) && (end_minute >= now_minute)))) {
                            DatabaseReference databaseReference_student = database.getReference("Enter/" + today + "/student");
                            databaseReference_student.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // 파이어베이스의 데이터를 받아오는 곳
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        EnterInfo enterInfo = snapshot.getValue(EnterInfo.class);
                                        if(enterInfo.getState().equals("입소전")) {
                                            databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("state").setValue("입소중");
                                        }
                                    }
                                    databaseReference.child(today).child("state").setValue("ing");
                                    reloadPage();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // DB를 가져오던 중 에러 발생 시
                                    Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if((now_hour > end_hour) || ((now_hour == end_hour) && (now_minute > end_minute))) {
                            // 입소 종료
                            DatabaseReference databaseReference_student = database.getReference("Enter/" + today + "/student");
                            databaseReference_student.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // 파이어베이스의 데이터를 받아오는 곳
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        EnterInfo enterInfo = snapshot.getValue(EnterInfo.class);
                                        if(enterInfo.getState().equals("입소중")) {
                                            databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("state").setValue("입소중(지각)");
                                        }
                                    }
                                    databaseReference.child(today).child("state").setValue("after");
                                    reloadPage();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // DB를 가져오던 중 에러 발생 시
                                    Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                    // state가 before?
                    // 현재시간이 입소시간?
                    // 학생 state->입소중
                    // state->ing

                    // state가 ing?
                    // 현재 시간이 입소시간 초과?
                    // 학생 state==입소중 ?
                    // 학생 state -> 입소중(지각)
                    // state->end
/*
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
                                Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

*/

                } else {
                    // 외출 가능 시간 설정 없음
                    String use = dataSnapshot.child("timeList").child("use").getValue().toString();
                    if(!use.equals("0")) {
                        // 외출 시간 예약 있음
                        String start_time = dataSnapshot.child("timeList").child(use).child("start").getValue().toString();
                        String end_time = dataSnapshot.child("timeList").child(use).child("end").getValue().toString();

                        DatabaseReference databaseReference = database.getReference("Enter");
                        databaseReference.child(today).child("time").child("start").setValue(start_time);
                        databaseReference.child(today).child("time").child("end").setValue(end_time);

                        // 학생 데이터 초기화
                        DatabaseReference databaseReference_student = database.getReference("StudentUser");
                        databaseReference_student.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // 파이어베이스의 데이터를 받아오는 곳
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    StudentUser student = snapshot.getValue(StudentUser.class);
                                    EnterInfo enterInfo = new EnterInfo(student.getEmail(), "", student.getName(), student.getRoom(), "입소전", 0);
                                    String key = student.getRoom() + student.getName();
                                    databaseReference.child(today).child("student").child(key).setValue(enterInfo);
                                }
                                reloadPage();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
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
        DatabaseReference databaseReference = database.getReference("Enter/" + today + "/student");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EnterInfo enterInfo = snapshot.getValue(EnterInfo.class);
                    arrayList.add(enterInfo);
                }
                //Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new EnterInfoCustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    private void createQRScanner() {

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setCameraId(0);
        qrScan.setPrompt("Scanning...");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(ManagerEnterActivity.this, "스캔취소", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                String sResult = result.getContents();
                String email = sResult.split(",")[0];
                double temp = Double.parseDouble(sResult.split(",")[1]);

                DatabaseReference databaseReference = database.getReference("Enter");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // 파이어베이스의 데이터를 받아오는 곳
                        for (DataSnapshot snapshot : dataSnapshot.child(today).child("student").getChildren()) {
                            EnterInfo enterInfo = snapshot.getValue(EnterInfo.class);
                            if(enterInfo.getEmail().equals(email)) {
                                if(temp >= 37.5) {
                                    Toast.makeText(ManagerEnterActivity.this, temp+"", Toast.LENGTH_SHORT).show();
                                    // 발열
                                    enterInfo.setState("입소불가");
                                    databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("state").setValue("입소불가");
                                }
                                else if(enterInfo.getState().equals("입소중")) {
                                    // 정상 입소
                                    enterInfo.setState("입소완료");
                                    databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("state").setValue("입소완료");
                                } else if(enterInfo.getState().equals("입소완료")) {
                                    // 지각
                                    enterInfo.setState("입소완료(지각)");
                                    databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("state").setValue("입소완료(지각)");
                                } else {
                                    Toast.makeText(ManagerEnterActivity.this, "입소중이 아닙니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                }

                                databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("temp").setValue(temp);
                                enterInfo.setTemp(temp);

                                Date now = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
                                databaseReference.child(today).child("student").child(enterInfo.getRoom()+enterInfo.getName()).child("enter_time").setValue(formatter.format(now));
                                enterInfo.setEnter_time(formatter.format(now));

                                Dialog dialog = new Dialog(ManagerEnterActivity.this);
                                dialog.setContentView(R.layout.activity_enter_info_popup);
                                dialog.setTitle("enter info");

                                TextView txt_student_info = dialog.findViewById(R.id.txt_student_info);
                                TextView txt_state = dialog.findViewById(R.id.txt_state);
                                TextView txt_enter_time = dialog.findViewById(R.id.txt_enter_time);
                                TextView txt_temp = dialog.findViewById(R.id.txt_temp);
                                Button btn_submit = dialog.findViewById(R.id.btn_submit);

                                txt_student_info.setText(enterInfo.getRoom() + "호 " + enterInfo.getName());
                                txt_state.setText(enterInfo.getState());
                                if(enterInfo.getState().equals("입소불가")) {
                                    int warningColor = ContextCompat.getColor(ManagerEnterActivity.this, R.color.colorWarning);
                                    txt_state.setTextColor(warningColor);
                                }
                                if(enterInfo.getTemp() == 0.0) {
                                    txt_temp.setText("-");
                                } else {
                                    txt_temp.setText(String.valueOf(enterInfo.getTemp()));
                                }
                                txt_enter_time.setText(enterInfo.getEnter_time());

                                btn_submit.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        reloadPage();
                                    }
                                });

                                dialog.show();

                                break;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // DB를 가져오던 중 에러 발생 시
                        Toast.makeText(ManagerEnterActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void reloadPage() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
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