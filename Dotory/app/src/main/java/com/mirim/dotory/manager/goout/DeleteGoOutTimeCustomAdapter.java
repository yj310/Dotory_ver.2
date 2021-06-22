package com.mirim.dotory.manager.goout;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirim.dotory.GoOutTime;
import com.mirim.dotory.R;

import java.util.ArrayList;

public class DeleteGoOutTimeCustomAdapter extends RecyclerView.Adapter<DeleteGoOutTimeCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<GoOutTime> arrayList;
    private Context context;
    private DeleteGoOutTimeActivity activity;

    public DeleteGoOutTimeCustomAdapter(ArrayList<GoOutTime> arrayList, Context context, DeleteGoOutTimeActivity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_go_out_time, parent, false);
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
            int basicColor = ContextCompat.getColor(context, R.color.colorBasic);
            holder.txt_start_time.setTextColor(basicColor);
            holder.txt_end_time.setTextColor(basicColor);
            holder.txt_tilde.setTextColor(basicColor);
        } else {
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
        ImageView btn_delete;
        GoOutTime goOutTime;
        boolean change = false;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_start_time = itemView.findViewById(R.id.txt_start_time);
            this.txt_end_time = itemView.findViewById(R.id.txt_end_time);
            this.txt_tilde = itemView.findViewById(R.id.txt_tilde);
            this.btn_delete = itemView.findViewById(R.id.btn_delete);
            this.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goOutTime.isUse()) {
                        Toast.makeText(context, "현재 사용중인 아이템은 삭제가 불가능합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 아이템 제거
                        AlertDialog.Builder alert_ex = new AlertDialog.Builder(context);
                        alert_ex.setMessage("정말로 제거하시겠습니까?");

                        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert_ex.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database = FirebaseDatabase.getInstance();
                                databaseReference = database.getReference("GoOut/timeList/" + goOutTime.getKey());
                                databaseReference.removeValue();

                                activity.reloadPage();
                            }
                        });

                        AlertDialog alert = alert_ex.create();
                        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alert.show();
                    }

                }
            });
        }




    }
}
