package com.mirim.dotory.manager.goout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.mirim.dotory.R;


public class AddGoOutTimePopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_go_out_time_popup);
    }
}