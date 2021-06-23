package com.mirim.dotory.manager.enter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.TimeItem;
import com.mirim.dotory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnterTimeCustomAdapter extends RecyclerView.Adapter<EnterTimeCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<TimeItem> arrayList;
    private Context context;
    private EnterSettingActivity activity;

    public EnterTimeCustomAdapter(ArrayList<TimeItem> arrayList, Context context, EnterSettingActivity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_time, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        TimeItem timeItem = arrayList.get(position);
        holder.timeItem = timeItem;

        holder.txt_start_time.setText(timeItem.getStart());
        holder.txt_end_time.setText(timeItem.getEnd());
        if(timeItem.isUse()) {
            holder.btn_switch.setChecked(true);
            int basicColor = ContextCompat.getColor(context, R.color.colorBasic);
            holder.txt_start_time.setTextColor(basicColor);
            holder.txt_end_time.setTextColor(basicColor);
            holder.txt_tilde.setTextColor(basicColor);
        } else {
            holder.btn_switch.setChecked(false);
            int blurColor = ContextCompat.getColor(context, R.color.colorTextBlur);
            holder.txt_start_time.setTextColor(blurColor);
            holder.txt_end_time.setTextColor(blurColor);
            holder.txt_tilde.setTextColor(blurColor);
        }

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_start_time;
        TextView txt_end_time;
        TextView txt_tilde;
        Switch btn_switch;
        TimeItem timeItem;
        boolean change = false;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_start_time = itemView.findViewById(R.id.txt_start_time);
            this.txt_end_time = itemView.findViewById(R.id.txt_end_time);
            this.txt_tilde = itemView.findViewById(R.id.txt_tilde);
            this.btn_switch = itemView.findViewById(R.id.btn_switch);
            this.btn_switch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    change = true;
                    if(btn_switch.isChecked()) {
                        //Toast.makeText(context, "on", Toast.LENGTH_SHORT).show();
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference("Enter/timeList/use");
                        databaseReference.setValue(timeItem.getKey());
                        databaseReference = database.getReference("Enter");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Date now;
                                now = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String today = formatter.format(now);
                                String use = String.valueOf(timeItem.getKey());

                                databaseReference = database.getReference("Enter");

                                String start_time = dataSnapshot.child("timeList").child(use).child("start").getValue().toString();
                                String end_time = dataSnapshot.child("timeList").child(use).child("end").getValue().toString();

                                databaseReference.child(today).child("time").child("start").setValue(start_time);
                                databaseReference.child(today).child("time").child("end").setValue(end_time);



                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Toast.makeText(context, error.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        // 페이지 리로드..
                        activity.reloadPage();
                        change = false;
                    } else {
                        //Toast.makeText(context, "off", Toast.LENGTH_SHORT).show();
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference("Enter/timeList/use");
                        databaseReference.setValue(0);

                        // 페이지 리로드..
                        activity.reloadPage();
                        change = false;
                    }
                }
            });
        }




    }
}
