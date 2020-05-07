package com.munchkin.musclediary.src.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Space;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class DeleteSettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_setting);

        //클릭 리스너 생성
        View.OnClickListener listener = onClickListener();

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_delete_setting);
        btBack.setOnClickListener(listener);
    }

    private View.OnClickListener onClickListener(){
        //리스너 모음
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    //배경클릭시뒤로가기
                    case R.id.bt_back_delete_setting:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
        return listener;
    }
}