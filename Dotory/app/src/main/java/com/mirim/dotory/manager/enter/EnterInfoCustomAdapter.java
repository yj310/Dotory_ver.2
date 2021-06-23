package com.mirim.dotory.manager.enter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirim.dotory.EnterInfo;
import com.mirim.dotory.R;

import java.util.ArrayList;

public class EnterInfoCustomAdapter extends RecyclerView.Adapter<EnterInfoCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<EnterInfo> arrayList;
    private Context context;

    public EnterInfoCustomAdapter(ArrayList<EnterInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enter_info, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        EnterInfo enterInfo = arrayList.get(position);
        holder.enterInfo = enterInfo;

        holder.txt_room.setText(String.valueOf(enterInfo.getRoom()));
        holder.txt_name.setText(enterInfo.getName());
        if(enterInfo.getTemp() == 0.0) {
            holder.txt_temp.setText("-");
        } else {
            holder.txt_temp.setText(String.valueOf(enterInfo.getTemp()));
        }
        holder.txt_state.setText(enterInfo.getState());
        if(enterInfo.getState().equals("외출중") || enterInfo.getState().equals("외출중(지각)")) {
            int basicColor = ContextCompat.getColor(context, R.color.colorBasic);
            holder.txt_room.setTextColor(basicColor);
            holder.txt_name.setTextColor(basicColor);
            holder.txt_state.setTextColor(basicColor);
        }
        if(enterInfo.getState().equals("입소불가")) {
            int basicColor = ContextCompat.getColor(context, R.color.colorWarning);
            holder.txt_room.setTextColor(basicColor);
            holder.txt_name.setTextColor(basicColor);
            holder.txt_state.setTextColor(basicColor);
            holder.iv_warning.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_room;
        TextView txt_name;
        TextView txt_temp;
        TextView txt_state;
        ImageView iv_warning;

        LinearLayout whole_area;
        EnterInfo enterInfo;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_room = itemView.findViewById(R.id.txt_room);
            this.txt_name = itemView.findViewById(R.id.txt_name);
            this.txt_temp = itemView.findViewById(R.id.txt_temp);
            this.txt_state = itemView.findViewById(R.id.txt_state);
            this.iv_warning = itemView.findViewById(R.id.iv_warning);

            this.whole_area = itemView.findViewById(R.id.whole_area);
            this.whole_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 팝업창 띄우기
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.activity_enter_info_popup);
                    dialog.setTitle("enter info");

                    TextView txt_student_info = dialog.findViewById(R.id.txt_student_info);
                    TextView txt_state = dialog.findViewById(R.id.txt_state);
                    TextView txt_enter_time = dialog.findViewById(R.id.txt_enter_time);
                    TextView txt_temp = dialog.findViewById(R.id.txt_temp);
                    Button btn_submit = dialog.findViewById(R.id.btn_submit);

                    txt_student_info.setText(enterInfo.getRoom() + "호 " + enterInfo.getName());
                    txt_state.setText(enterInfo.getState());
                    if(enterInfo.getTemp() == 0.0) {
                        txt_temp.setText("-");
                    } else {
                        txt_temp.setText(String.valueOf(enterInfo.getTemp()));
                    }
                    txt_enter_time.setText(enterInfo.getEnter_time());

                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();


                }
            });
        }




    }
}
