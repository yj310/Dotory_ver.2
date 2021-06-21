package com.mirim.dotory.manager.goout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mirim.dotory.GoOutInfo;
import com.mirim.dotory.R;

public class GoOutInfoPopupActivity extends Activity {

    private TextView txt_state;
    private TextView txt_place;
    private TextView txt_reason;
    private TextView txt_go_out_time;
    private TextView txt_expect_time;
    private TextView txt_enter_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_go_out_info_popup);

        Intent intent = getIntent();
        GoOutInfo goOutInfo = (GoOutInfo) intent.getSerializableExtra("goOutInfo");

        txt_state = findViewById(R.id.txt_state);
        txt_place = findViewById(R.id.txt_place);
        txt_reason = findViewById(R.id.txt_reason);
        txt_go_out_time = findViewById(R.id.txt_go_out_time);
        txt_expect_time = findViewById(R.id.txt_expect_time);
        txt_enter_time = findViewById(R.id.txt_enter_time);

        txt_state.setText(goOutInfo.getState());
        txt_place.setText(goOutInfo.getPlace());
        txt_reason.setText(goOutInfo.getReason());
        txt_go_out_time.setText(goOutInfo.getGo_out_time());
        txt_expect_time.setText(goOutInfo.getExpect_time());
        txt_enter_time.setText(goOutInfo.getEnter_time());
    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        /*if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }*/
        return true;
    }


    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}