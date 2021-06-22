package com.mirim.dotory.manager.goout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.GoOutTime;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerGoOutActivity;
import com.mirim.dotory.manager.post.ModifyPostActivity;
import com.mirim.dotory.student.StudentGoOutActivity;

import java.util.ArrayList;

public class GoOutSettingActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GoOutTime> arrayList;

    private TextView txt_start_time;
    private TextView txt_end_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_out_setting);


        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_add_item).setOnClickListener(onClickListener);
        findViewById(R.id.btn_more).setOnClickListener(onClickListener);

        txt_start_time = findViewById(R.id.txt_start_time);
        txt_end_time = findViewById(R.id.txt_end_time);


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
                    if(!snapshot.getKey().toString().equals("use")) {
                        GoOutTime goOutTime = new GoOutTime(Integer.parseInt(snapshot.getKey()), false, snapshot.child("start").getValue().toString(), snapshot.child("end").getValue().toString());
                        if(snapshot.getKey().equals(useKey)) {
                            goOutTime.setUse(true);
                            txt_start_time.setText(goOutTime.getStart());
                            txt_end_time.setText(goOutTime.getEnd());
                        }
                        arrayList.add(goOutTime);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_add_item:
                    Intent popupIntent = new Intent(GoOutSettingActivity.this, GoOutInfoPopupActivity.class);
                    startActivity(popupIntent);
                    break;
                case R.id.btn_more:
                    PopupMenu popup = new PopupMenu(GoOutSettingActivity.this, v);
                    popup.getMenuInflater().inflate(R.menu.go_out_time, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle().equals("삭제")){
/*
                                database = FirebaseDatabase.getInstance();
                                databaseReference = database.getReference("Post/" + id);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Post post = dataSnapshot.getValue(Post.class);
                                        post.setVisible(false);
                                        databaseReference.setValue(post);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context, "데이터베이스 오류\n" + databaseError.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(context.getApplicationContext(), "삭제가 완료되었습니다. ", Toast.LENGTH_SHORT).show();*/
                            }

                            return false;
                        }
                    });
                    popup.show();
                    break;

            }
        }
    };


    public void reloadPage() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}