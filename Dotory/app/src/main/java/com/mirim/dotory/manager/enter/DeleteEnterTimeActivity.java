package com.mirim.dotory.manager.enter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.R;
import com.mirim.dotory.TimeItem;

import java.util.ArrayList;

public class DeleteEnterTimeActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TimeItem> arrayList;

    private TextView txt_start_time;
    private TextView txt_end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_go_out_time);

        findViewById(R.id.btn_back).setOnClickListener(onClickListener);

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
                    Intent popupIntent = new Intent(DeleteEnterTimeActivity.this, EnterSettingActivity.class);
                    startActivity(popupIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        }
    };

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Enter/timeList");
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
                Toast.makeText(DeleteEnterTimeActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new DeleteEnterTimeCustomAdapter(arrayList, this, this);
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