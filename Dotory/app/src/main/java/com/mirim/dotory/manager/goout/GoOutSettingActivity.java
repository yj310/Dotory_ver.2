package com.mirim.dotory.manager.goout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.TimeItem;
import com.mirim.dotory.R;

import java.util.ArrayList;

public class GoOutSettingActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TimeItem> arrayList;

    private TextView txt_start_time;
    private TextView txt_end_time;

    private int start_hour;
    private int start_minute;
    private int end_hour;
    private int end_minute;
    private String add_popup_select_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_out_setting);


        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_add_item).setOnClickListener(onClickListener);
        findViewById(R.id.btn_more).setOnClickListener(onClickListener);

        txt_start_time = findViewById(R.id.txt_start_time);
        txt_end_time = findViewById(R.id.txt_end_time);

        setRecyclerView();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_add_item:
                    createAddGoOutTimePopup();
                    break;
                case R.id.btn_more:
                    createMorePopup(view);
                    break;

            }
        }
    };

    private void createAddGoOutTimePopup() {
        Dialog dialog = new Dialog(GoOutSettingActivity.this);
        dialog.setContentView(R.layout.activity_add_go_out_time_popup);
        dialog.setTitle("Add Item");

        Button btn_cancle = dialog.findViewById(R.id.btn_cancel);
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        TextView btn_set_start = dialog.findViewById(R.id.btn_set_start);
        TextView btn_set_end = dialog.findViewById(R.id.btn_set_end);
        TimePicker timePicker = dialog.findViewById(R.id.time_picker);

        timePicker.setHour(0);
        timePicker.setMinute(0);

        start_hour = 0;
        start_minute = 0;
        end_hour = 0;
        end_minute = 0;
        add_popup_select_state = "start";

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(add_popup_select_state.equals("start")) {
                    start_hour = timePicker.getHour();
                    start_minute = timePicker.getMinute();
                } else if(add_popup_select_state.equals("end")) {
                    end_hour = timePicker.getHour();
                    end_minute = timePicker.getMinute();
                }

                if( (start_hour < end_hour) || ((start_hour==end_hour)&&(start_minute < end_hour)) ) {
                    // 아이템 추가
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("GoOut/timeList/lastkey");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // 파이어베이스의 데이터를 받아오는 곳
                            int useKey = Integer.parseInt(dataSnapshot.getValue().toString());
                            useKey++;
                            DatabaseReference databaseReference = database.getReference("GoOut/timeList/");
                            databaseReference.child(String.valueOf(useKey)).child("start").setValue(start_hour + ":" + ((start_minute < 10) ? (start_minute + "0") : (start_minute)));
                            databaseReference.child(String.valueOf(useKey)).child("end").setValue(end_hour + ":" + ((end_minute < 10) ? (end_minute + "0") : (end_minute)));
                            databaseReference.child("lastkey").setValue(useKey);

                            dialog.cancel();
                            Toast.makeText(GoOutSettingActivity.this, "추가가 완료되었습니다. ", Toast.LENGTH_SHORT).show();
                            reloadPage();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // DB를 가져오던 중 에러 발생 시
                            Toast.makeText(GoOutSettingActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(GoOutSettingActivity.this, "시간 설정이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_set_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(add_popup_select_state.equals("end")) {
                    // 화면이 변경되었다면
                    add_popup_select_state = "start";
                    btn_set_start.setTextColor(ContextCompat.getColor(GoOutSettingActivity.this, R.color.colorBasic));
                    btn_set_end.setTextColor(ContextCompat.getColor(GoOutSettingActivity.this, R.color.colorTextBlur));
                    end_hour = timePicker.getHour();
                    end_minute = timePicker.getMinute();
                    timePicker.setHour(start_hour);
                    timePicker.setMinute(start_minute);
                }
            }
        });
        btn_set_end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(add_popup_select_state.equals("start")) {
                    // 화면이 변경되었다면
                    add_popup_select_state = "end";
                    btn_set_start.setTextColor(ContextCompat.getColor(GoOutSettingActivity.this, R.color.colorTextBlur));
                    btn_set_end.setTextColor(ContextCompat.getColor(GoOutSettingActivity.this, R.color.colorBasic));
                    start_hour = timePicker.getHour();
                    start_minute = timePicker.getMinute();
                    timePicker.setHour(end_hour);
                    timePicker.setMinute(end_minute);
                }
            }
        });

        dialog.show();
    }

    private void createMorePopup(View view) {
        PopupMenu popup = new PopupMenu(GoOutSettingActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.setting_time, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("삭제")){
                    Intent popupIntent = new Intent(GoOutSettingActivity.this, DeleteGoOutTimeActivity.class);
                    overridePendingTransition(0, 0);
                    startActivity(popupIntent);
                    finish();
                }
                return false;
            }
        });
        popup.show();
    }

    private void setRecyclerView() {

        database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GoOut/timeList");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스의 데이터를 받아오는 곳
                arrayList.clear();
                String useKey = dataSnapshot.child("use").getValue().toString();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(!(snapshot.getKey().toString().equals("use") || snapshot.getKey().toString().equals("lastkey"))) {
                        TimeItem timeItem = new TimeItem(Integer.parseInt(snapshot.getKey()), false, snapshot.child("start").getValue().toString(), snapshot.child("end").getValue().toString());
                        if(snapshot.getKey().equals(useKey)) {
                            timeItem.setUse(true);
                            txt_start_time.setText(timeItem.getStart());
                            txt_end_time.setText(timeItem.getEnd());
                        }
                        arrayList.add(timeItem);
                    }
                }
                //Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Toast.makeText(GoOutSettingActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new GoOutTimeCustomAdapter(arrayList, this, this);
        recyclerView.setAdapter(adapter);
    }

    public void reloadPage() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}