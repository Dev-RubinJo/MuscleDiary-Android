package com.munchkin.musclediary.src.main.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class InputSettingActivity extends BaseActivity implements View.OnClickListener {

    FirebaseAnalytics mFirebaseAnalytics;
    @SuppressLint("InvalidAnalyticsName")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_setting);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle params = new Bundle();
        params.putString("req", "deleted_activity(InputDishCategory)_android");
        mFirebaseAnalytics.logEvent("deleted_activity(InputDishCategory)_android", params);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_input_setting);
        btBack.setOnClickListener(this);

        //입력완료 버튼클릭 설정
        Button btComplete = findViewById(R.id.bt_complete_input_setting);
        btComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //배경클릭시뒤로가기
            case R.id.bt_back_input_setting:
                finish();
                break;

            case R.id.bt_complete_input_setting:
                //입력완료 버튼 클릭 이벤트
                Intent intent = new Intent();
                EditText etInput = findViewById(R.id.et_input_setting);
                String input = etInput.getText().toString();
                intent.putExtra("input", input);
                setResult(RESULT_OK, intent);
                finish();
                break;

            default:
                break;
        }
    }
}
