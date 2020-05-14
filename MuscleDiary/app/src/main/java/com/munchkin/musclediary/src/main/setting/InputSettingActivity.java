package com.munchkin.musclediary.src.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Space;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class InputSettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_setting);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_input_setting);
        btBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //배경클릭시뒤로가기
            case R.id.bt_back_input_setting:
                finish();
                break;

            case R.id.bt_complete_input_setting:
                break;

            default:
                break;
        }
    }
}
