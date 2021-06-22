package com.mirim.dotory.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mirim.dotory.R;

public class StudentEnterActivity extends AppCompatActivity {

    private String email;
    private ImageView iv_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enter);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        findViewById(R.id.btn_bottombar_board).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_goout).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_enter).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_point).setOnClickListener(onClickListener);
        findViewById(R.id.btn_bottombar_my).setOnClickListener(onClickListener);



        iv_qr = findViewById(R.id.img_qr);

        createQRcode();
    }

    public void createQRcode() {
        String text = email;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv_qr.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_bottombar_board:
                    intent = new Intent(StudentEnterActivity.this, StudentBoardActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_goout:
                    intent = new Intent(StudentEnterActivity.this, StudentGoOutActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_enter:
                    intent = new Intent(StudentEnterActivity.this, StudentEnterActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_point:
                    intent = new Intent(StudentEnterActivity.this, StudentPointActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.btn_bottombar_my:
                    intent = new Intent(StudentEnterActivity.this, StudentMyActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;

            }
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");

        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert_ex.setTitle("Dotory");
        AlertDialog alert = alert_ex.create();
        alert.show();

    }

}