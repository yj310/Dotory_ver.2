package com.mirim.dotory.manager.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mirim.dotory.Post;
import com.mirim.dotory.R;

import java.io.InputStream;

public class ModifyPostActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private EditText et_title;
    private EditText et_content;
    private ImageView imageView;
    private Post post;

    private Uri filePath;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_modify_post);

        et_title = findViewById(R.id.input_title);
        et_content = findViewById(R.id.input_content);
        imageView = (ImageView)findViewById(R.id.image);
        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");


            findViewById(R.id.btn_back).setOnClickListener(onClickListener);
            findViewById(R.id.btn_add_picture).setOnClickListener(onClickListener);
            findViewById(R.id.btn_modify_post).setOnClickListener(onClickListener);
            findViewById(R.id.btn_remove_picture).setOnClickListener(onClickListener);

            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Post/" + id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    post = dataSnapshot.getValue(Post.class);
                    et_title.setText(post.getTitle());
                    et_content.setText(post.getContent());

                    if(post.getImg_url().length() > 0) {
                        FirebaseStorage fs = FirebaseStorage.getInstance();
                        StorageReference imagesRef = fs.getReference().child("postImages/" + post.getImg_url());

                        Activity activity = (Activity) ModifyPostActivity.this;
                        if (activity.isFinishing())
                            return;

                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //????????? ?????? ?????????

                                Glide.with(ModifyPostActivity.this)
                                        .load(uri)
                                        .into(imageView);

                                findViewById(R.id.btn_remove_picture).setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //????????? ?????? ?????????
                                //Toast.makeText(ModifyPostActivity.this, imagesRef.toString() + " ?????? ??????", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ModifyPostActivity.this, "?????????????????? ??????\n" + databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "????????? ?????????????????????. \n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
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
                    post.setImg_url("");
                    findViewById(R.id.btn_remove_picture).setVisibility(View.INVISIBLE);
                    break;
                case R.id.btn_modify_post:
                    postModify();
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        try {

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(Exception e)
        {
            Toast.makeText(this, "onActivityResult\n"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean uploadFile() {
        try {
            //???????????? ????????? ????????? ??????
            if (filePath != null) {
                //????????? ?????? Dialog ?????????

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("????????????...");
                progressDialog.show();

                //storage
                FirebaseStorage storage = FirebaseStorage.getInstance();
                final String filename;
                final String prevfilename = post.getImg_url();
                //Unique??? ???????????? ?????????.
                if(post.getImg_url().length() <= 0)
                {
                    filename = post.getDate().replaceAll("-", "") + post.getTime().replaceAll(":", "") + ".jpg";
                } else if(!post.getImg_url().contains("_"))
                {
                    filename = post.getImg_url().substring(0, post.getImg_url().indexOf(".")) + "_0" + ".jpg" ;
                } else
                {
                    filename = post.getImg_url().substring(0, post.getImg_url().indexOf("_") + 1)
                            + Integer.toString((post.getImg_url().charAt(post.getImg_url().indexOf("_") + 1) - '0' + 1)) + ".jpg";
                }

                storage.getReference().child("postImages/" + prevfilename).delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

                //storage ????????? ?????? ???????????? ????????? ??????.
                StorageReference storageRef = storage.getReferenceFromUrl("gs://dotory2.appspot.com").child("postImages/" + filename);
                //???????????????...
                storageRef.putFile(filePath)
                        //?????????
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss(); //????????? ?????? Dialog ?????? ??????=
                                //Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                                post.setImg_url(filename);
                                databaseReference.setValue(post);
                                Toast.makeText(ModifyPostActivity.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        //?????????
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                                finish();
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
                return true;
            } else {
                // ????????? ?????????
               return false;
            }
        } catch(Exception e) {
            Toast.makeText(this, "uploadFile()\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    void postModify() {

        try {
            if (et_title.getText().length() <= 0 || et_content.getText().length() <= 0) {
                Toast.makeText(ModifyPostActivity.this, "???????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {

                post.setTitle(et_title.getText().toString());
                post.setContent(et_content.getText().toString());

                if(!uploadFile()) {
                    databaseReference.setValue(post);
                    Toast.makeText(ModifyPostActivity.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                    finish();
                }


            } catch (Exception e) {
                Toast.makeText(this, "?????????????????? ??????\n" + e.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch(Exception e) {
            Toast.makeText(this, "postModify\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }



    }
}