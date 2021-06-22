package com.mirim.dotory.manager.goout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.GoOutTime;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerGoOutActivity;
import com.mirim.dotory.manager.post.ModifyPostActivity;

import java.util.ArrayList;

public class GoOutTimeCustomAdapter extends RecyclerView.Adapter<GoOutTimeCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<GoOutTime> arrayList;
    private Context context;
    private GoOutSettingActivity activity;

    public GoOutTimeCustomAdapter(ArrayList<GoOutTime> arrayList, Context context, GoOutSettingActivity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.go_out_time, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        GoOutTime goOutTime = arrayList.get(position);
        holder.goOutTime = goOutTime;

        holder.txt_start_time.setText(goOutTime.getStart());
        holder.txt_end_time.setText(goOutTime.getEnd());
        if(goOutTime.isUse()) {
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
        GoOutTime goOutTime;
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
                        databaseReference = database.getReference("GoOut/timeList/use");
                        databaseReference.setValue(goOutTime.getKey());

                        // 페이지 리로드..
                        activity.reloadPage();
                        change = false;
                    } else {
                        //Toast.makeText(context, "off", Toast.LENGTH_SHORT).show();
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference("GoOut/timeList/use");
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
