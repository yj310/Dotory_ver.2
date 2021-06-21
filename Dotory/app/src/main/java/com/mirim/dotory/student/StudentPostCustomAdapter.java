package com.mirim.dotory.student;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentPostCustomAdapter extends RecyclerView.Adapter<StudentPostCustomAdapter.PostCustomViewHolder> {
    private ArrayList<Post> arrayList;
    private Context context;

    public StudentPostCustomAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
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

        public PostCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.text_title);
            this.tv_date = itemView.findViewById(R.id.text_date);
            this.tv_content = itemView.findViewById(R.id.text_content);
            this.iv_post_img = itemView.findViewById(R.id.post_img);



        }
    }
}
