package com.mirim.dotory.manager.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private ImageView imageView;
    private EditText input_title;
    private EditText input_content;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_create_post);

        input_title = findViewById(R.id.input_title);
        input_content = findViewById(R.id.input_content);
        imageView = (ImageView)findViewById(R.id.image);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.btn_add_picture).setOnClickListener(onClickListener);
        findViewById(R.id.btn_upload_post).setOnClickListener(onClickListener);
        findViewById(R.id.btn_remove_picture).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_add_picture:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.btn_remove_picture:
                    filePath = null;
                    imageView.setImageBitmap(null);
                    findViewById(R.id.btn_remove_picture).setVisibility(View.INVISIBLE);
                    break;
                case R.id.btn_upload_post:
                    postUpload();
                    break;

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    // ????????? ??????????????? ????????? ??????
                    InputStream in = getContentResolver().openInputStream(filePath);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // ????????? ??????
                    imageView.setImageBitmap(img);
                    findViewById(R.id.btn_remove_picture).setVisibility(View.VISIBLE);

                    /*LayoutParams params = (LayoutParams) imageView.getLayoutParams();
                    params.height = ;
                    imageView.setLayoutParams(params);*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadFile() {
        //???????????? ????????? ????????? ??????
        if (filePath != null) {
            //????????? ?????? Dialog ?????????
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("????????????...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique??? ???????????? ?????????.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".jpg";

            //storage ????????? ?????? ???????????? ????????? ??????.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://dotory2.appspot.com").child("postImages/" + filename);

            //???????????????...
            storageRef.putFile(filePath)
                    //?????????
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //????????? ?????? Dialog ?????? ??????

                            Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    //?????????
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //?????????
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //?????? ?????? ?????? ???????????? ????????? ????????????. ??? ??????????
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog??? ???????????? ???????????? ????????? ??????
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    void postUpload() {

        if(input_title.getText().length() <= 0 || input_content.getText().length() <= 0) {
            Toast.makeText(CreatePostActivity.this, "???????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            SimpleDateFormat formatID = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            Date time = new Date();

            uploadFile();
            Post post;
            if (filePath != null){
                post = new Post(
                        input_title.getText().toString(),
                        input_content.getText().toString(),
                        formatDate.format(time),
                        formatTime.format(time),
                        formatID.format(time) + ".jpg",
                        true
                );

                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("Post");
                databaseReference.child(formatID.format(time)).setValue(post);


            } else {
                post = new Post(
                        input_title.getText().toString(),
                        input_content.getText().toString(),
                        formatDate.format(time),
                        formatTime.format(time),
                        "",
                        true
                );

                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("Post");
                databaseReference.child(formatID.format(time)).setValue(post);


                Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                finish();
            }


        }catch(Exception e){
            Toast.makeText(this, "?????????????????? ??????\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }



    }
}