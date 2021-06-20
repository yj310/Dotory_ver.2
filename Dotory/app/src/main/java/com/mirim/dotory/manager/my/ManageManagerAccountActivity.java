package com.mirim.dotory.manager.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirim.dotory.R;
import com.mirim.dotory.manager.ManagerBoardActivity;
import com.mirim.dotory.manager.ManagerEnterActivity;
import com.mirim.dotory.manager.ManagerGoOutActivity;
import com.mirim.dotory.manager.ManagerMyActivity;
import com.mirim.dotory.manager.ManagerPointActivity;

public class ManageManagerAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_my_manage_manager_account);

        findViewById(R.id.btn_add_account).setOnClickListener(onClickListener);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
                case R.id.btn_add_account:
                    intent = new Intent(ManageManagerAccountActivity.this, AddManagerAccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    finish();
                    break;

            }
        }
    };
}