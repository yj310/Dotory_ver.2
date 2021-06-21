package com.mirim.dotory.manager.goout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.mirim.dotory.Post;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.post.ModifyPostActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GoOutInfoCustomAdapter extends RecyclerView.Adapter<GoOutInfoCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<GoOutInfo> arrayList;
    private Context context;

    public GoOutInfoCustomAdapter(ArrayList<GoOutInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.go_out, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        GoOutInfo goOutInfo = arrayList.get(position);
        holder.goOutInfo = goOutInfo;

        holder.txt_room.setText(String.valueOf(goOutInfo.getRoom()));
        holder.txt_name.setText(goOutInfo.getName());
        holder.txt_state.setText(goOutInfo.getState());
        if(goOutInfo.getState().equals("외출중")) {
            int basicColor = ContextCompat.getColor(context, R.color.colorBasic);
            holder.txt_room.setTextColor(basicColor);
            holder.txt_name.setTextColor(basicColor);
            holder.txt_state.setTextColor(basicColor);
            holder.iv_point.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_room;
        TextView txt_name;
        TextView txt_state;
        ImageView iv_point;
        LinearLayout whole_area;
        GoOutInfo goOutInfo;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_room = itemView.findViewById(R.id.txt_room);
            this.txt_name = itemView.findViewById(R.id.txt_name);
            this.txt_state = itemView.findViewById(R.id.txt_state);
            this.iv_point = itemView.findViewById(R.id.iv_point);

            this.whole_area = itemView.findViewById(R.id.whole_area);
            this.whole_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 팝업창 띄우기
                    Intent intent = new Intent(context, GoOutInfoPopupActivity.class);
                    intent.putExtra("goOutInfo", goOutInfo);
                    ((Activity) context).startActivity(intent);

                }
            });
        }




    }
}
