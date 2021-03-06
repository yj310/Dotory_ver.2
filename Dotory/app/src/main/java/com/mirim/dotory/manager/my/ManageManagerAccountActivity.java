package com.mirim.dotory.manager.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerBoardActivity;
import com.mirim.dotory.manager.ManagerEnterActivity;
import com.mirim.dotory.manager.ManagerGoOutActivity;
import com.mirim.dotory.manager.ManagerMyActivity;
import com.mirim.dotory.manager.ManagerPointActivity;
import com.mirim.dotory.manager.ManagerUser;
import com.mirim.dotory.manager.goout.GoOutInfoCustomAdapter;

import java.util.ArrayList;

public class ManageManagerAccountActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ManagerUser> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_manage_manager_account);

        database = FirebaseDatabase.getInstance();

        findViewById(R.id.btn_add_account).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);

        setRecyclerView();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_add_account:
                    intent = new Intent(ManageManagerAccountActivity.this, AddManagerAccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_back:
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
        DatabaseReference databaseReference = database.getReference("ManagerUser");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ????????????????????? ???????????? ???????????? ???
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(!snapshot.getKey().toString().equals("password")) {
                        ManagerUser managerUser = snapshot.getValue(ManagerUser.class);
                        arrayList.add(managerUser);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB??? ???????????? ??? ?????? ?????? ???
                Toast.makeText(ManageManagerAccountActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ManagerInfoCustomAdapter(arrayList, this, this);
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