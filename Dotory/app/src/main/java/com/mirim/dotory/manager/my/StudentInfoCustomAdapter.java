package com.mirim.dotory.manager.my;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerUser;
import com.mirim.dotory.manager.post.ModifyPostActivity;
import com.mirim.dotory.student.StudentUser;

import java.util.ArrayList;
import java.util.Map;

public class StudentInfoCustomAdapter extends RecyclerView.Adapter<StudentInfoCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<StudentUser> arrayList;
    private Context context;
    private ManageStudentAccountActivity activity;

    public StudentInfoCustomAdapter(ArrayList<StudentUser> arrayList, Context context, ManageStudentAccountActivity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_student, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        StudentUser studentUser = arrayList.get(position);
        holder.studentUser = studentUser;

        holder.txt_name.setText(studentUser.getRoom() + "??? " + studentUser.getName());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        ImageView btn_more;
        StudentUser studentUser;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_name = itemView.findViewById(R.id.txt_name);

            this.btn_more = itemView.findViewById(R.id.btn_more);
            this.btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // ????????? ?????????
                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.manage_student, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("??????")){
                                // ??????
                                Intent intent = new Intent(context, OpenStudentInfoActivity.class);
                                intent.putExtra("email", studentUser.getEmail());
                                context.startActivity(intent);
                            }
                            if(item.getTitle().equals("??????")){
                                // ??????
                                Intent intent = new Intent(context, ModifyStudentInfoActivity.class);
                                intent.putExtra("email", studentUser.getEmail());
                                context.startActivity(intent);
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });
        }




    }
}
