package com.mirim.dotory.manager.my;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerUser;
import com.mirim.dotory.manager.goout.DeleteGoOutTimeActivity;
import com.mirim.dotory.manager.goout.GoOutSettingActivity;

import java.util.ArrayList;

public class ManagerInfoCustomAdapter extends RecyclerView.Adapter<ManagerInfoCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<ManagerUser> arrayList;
    private Context context;
    private ManageManagerAccountActivity activity;

    public ManagerInfoCustomAdapter(ArrayList<ManagerUser> arrayList, Context context, ManageManagerAccountActivity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_manager, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        ManagerUser managerUser = arrayList.get(position);
        holder.managerUser = managerUser;

        holder.txt_name.setText(managerUser.getName());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        ImageView iv_profile;
        ImageView btn_more;
        ManagerUser managerUser;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_name = itemView.findViewById(R.id.txt_name);
            this.iv_profile = itemView.findViewById(R.id.image_profile);

            this.btn_more = itemView.findViewById(R.id.btn_more);
            this.btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 팝업창 띄우기
                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.setting_time, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("삭제")){

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
                                        AlertDialog.Builder alert_ex2 = new AlertDialog.Builder(context);
                                        alert_ex2.setMessage("패스워드를 입력하시오.");

                                        alert_ex2.setView(R.layout.alert_dialog);

                                        alert_ex2.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        alert_ex2.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                database = FirebaseDatabase.getInstance();
                                                DatabaseReference databaseReference = database.getReference("ManagerUser");
                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        // 파이어베이스의 데이터를 받아오는 곳
                                                        Dialog dial = (Dialog) dialog;
                                                        EditText input = (EditText) dial.findViewById(R.id.addboxdialog);
                                                        if(dataSnapshot.child("password").getValue().toString().equals(input.getText().toString()) ) {
                                                            databaseReference.child(managerUser.getId()).removeValue();
                                                            Toast.makeText(context, "계정이 제거되었습니다.", Toast.LENGTH_SHORT).show();
                                                            activity.reloadPage();
                                                        } else {
                                                            Toast.makeText(context, "패스워드가 옳지 않습니다. ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        // DB를 가져오던 중 에러 발생 시
                                                        Toast.makeText(context, error.toException().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });

                                        AlertDialog alert2 = alert_ex2.create();
                                        alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        alert2.show();

                                    }
                                });

                                AlertDialog alert = alert_ex.create();
                                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alert.show();
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
