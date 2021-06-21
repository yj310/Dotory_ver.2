package com.mirim.dotory.manager.post;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.mirim.dotory.R;
import com.mirim.dotory.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagerPostCustomAdapter extends RecyclerView.Adapter<ManagerPostCustomAdapter.PostCustomViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Post> arrayList;
    private Context context;

    public ManagerPostCustomAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_post, parent, false);
        PostCustomViewHolder holder = new PostCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostCustomViewHolder holder, int position) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String today = formatter.format(now);
        if(arrayList.get(position).getDate().equals(today))
        {
            holder.tv_date.setText("오늘   "+ arrayList.get(position).getTime().substring(0, 5));
        } else
        {
            holder.tv_date.setText(arrayList.get(position).getDate());
        }

        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_content.setText(arrayList.get(position).getContent());
        holder.id = arrayList.get(position).getDate().replaceAll("-", "") + arrayList.get(position).getTime().replaceAll(":", "");

        if(arrayList.get(position).getImg_url().length() > 0)
        {
            FirebaseStorage fs = FirebaseStorage.getInstance();
            StorageReference imagesRef = fs.getReference().child("postImages/"+arrayList.get(position).getImg_url());

            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //이미지 로드 성공시

                    Glide.with(context.getApplicationContext())
                            .load(uri)
                            .into(holder.iv_post_img);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //이미지 로드 실패시
                    //Toast.makeText(context, imagesRef.toString() + " 로드 실패", Toast.LENGTH_SHORT).show();
                }
            });

            /*
            Glide.with(holder.itemView)
                    .load(imagesRef)
                    .into(holder.iv_post_img);*/
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostCustomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_date;
        TextView tv_content;
        ImageView iv_post_img;
        ImageView iv_menu;
        String id;

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.text_title);
            this.tv_date = itemView.findViewById(R.id.text_date);
            this.tv_content = itemView.findViewById(R.id.text_content);
            this.iv_post_img = itemView.findViewById(R.id.post_img);
            this.iv_menu = itemView.findViewById(R.id.btn_menu);
            this.iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.manager_post_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle().equals("수정")){
                                Intent intent = new Intent(context, ModifyPostActivity.class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            } else if(item.getTitle().equals("삭제")){

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
                                Toast.makeText(context.getApplicationContext(), "삭제가 완료되었습니다. ", Toast.LENGTH_SHORT).show();
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
