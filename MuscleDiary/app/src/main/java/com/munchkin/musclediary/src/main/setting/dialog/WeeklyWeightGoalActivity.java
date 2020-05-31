package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class WeeklyWeightGoalActivity extends BaseActivity implements View.OnClickListener {

    private int mWeightGoal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_weight_goal);

        mWeightGoal = getIntent().getIntExtra("weight_goal", 0);


        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radiogroup_weight_goal_setting);
        radioGroup.setOnCheckedChangeListener(listener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_weight_goal_setting);
        TextView btSelect = findViewById(R.id.bt_select_weight_goal_setting);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_weight_goal_setting);
        btBack.setOnClickListener(this);

        if(mWeightGoal == 1){
            radioGroup.check(R.id.radio_m500g_setting);
        } else if(mWeightGoal == 2){
            radioGroup.check(R.id.radio_m200g_setting);
        }
        else if(mWeightGoal == 3){
            radioGroup.check(R.id.radio_0g_setting);
        }
        else if(mWeightGoal == 4){
            radioGroup.check(R.id.radio_200g_setting);
        }
        else if(mWeightGoal == 5){
            radioGroup.check(R.id.radio_500g_setting);
        }
    }

    //라디오 그룹 리스너 설정
    private RadioGroup.OnCheckedChangeListener listener = (new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_m500g_setting:
                    mWeightGoal = 1;
                    break;

                case R.id.radio_m200g_setting:
                    mWeightGoal = 2;
                    break;

                case R.id.radio_0g_setting:
                mWeightGoal = 3;
                    break;

                case R.id.radio_200g_setting:
                mWeightGoal = 4;
                    break;

                case R.id.radio_500g_setting:
                mWeightGoal = 5;
                    break;

                default:
                    break;
            }

        }
    });

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_weight_goal_setting:
                finish();
                break;
            case R.id.bt_cancle_weight_goal_setting:
                finish();
                break;
            case R.id.bt_select_weight_goal_setting:
                Intent intent = new Intent();
                intent.putExtra("weight_goal", mWeightGoal);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
