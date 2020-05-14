package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class KcalGoalActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcal_goal);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_kcal_goal);
        btBack.setOnClickListener(this);

        //입력완료 버튼클릭 설정
        Button btComplete = findViewById(R.id.bt_complete_kcal_goal);
        btComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //배경클릭시뒤로가기
            case R.id.bt_back_kcal_goal:
                finish();
                break;

            case R.id.bt_complete_kcal_goal:
                //입력완료 버튼 클릭 이벤트
                Intent intent = new Intent();
                EditText etKcal = findViewById(R.id.et_kcal_goal);
                String stringKcal = etKcal.getText().toString();
                if (!stringKcal.equals("")) {
                    int kcal = Integer.parseInt(stringKcal);
                    intent.putExtra("kcal", kcal);
                }

                setResult(RESULT_OK, intent);
                finish();
                break;

            default:
                break;
        }
    }
}
