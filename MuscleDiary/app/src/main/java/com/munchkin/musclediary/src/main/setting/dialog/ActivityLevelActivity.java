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

public class ActivityLevelActivity extends BaseActivity implements View.OnClickListener {

    private int mActivityLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_level);

        mActivityLevel = getIntent().getIntExtra("active_level", 0);


        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radiogroup_activity_level_setting);
        radioGroup.setOnCheckedChangeListener(listener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_activity_level_setting);
        TextView btSelect = findViewById(R.id.bt_select_activity_level_setting);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_activity_level_setting);
        btBack.setOnClickListener(this);

        if(mActivityLevel == 1){
            radioGroup.check(R.id.radio_sedentary_active_setting);
        } else if(mActivityLevel == 2){
            radioGroup.check(R.id.radio_lightly_active_setting);
        }
        else if(mActivityLevel == 3){
            radioGroup.check(R.id.radio_moderately_active_setting);
        }
        else if(mActivityLevel == 4){
            radioGroup.check(R.id.radio_very_active_setting);
        }
        else if(mActivityLevel == 5){
            radioGroup.check(R.id.radio_extremely_active_setting);
        }
    }

    //라디오 그룹 리스너 설정
    private RadioGroup.OnCheckedChangeListener listener = (new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_sedentary_active_setting:
                    mActivityLevel = 1;
                    break;

                case R.id.radio_lightly_active_setting:
                    mActivityLevel = 2;
                    break;

                case R.id.radio_moderately_active_setting:
                    mActivityLevel = 3;
                    break;

                case R.id.radio_very_active_setting:
                    mActivityLevel = 4;
                    break;

                case R.id.radio_extremely_active_setting:
                    mActivityLevel = 5;
                    break;

                default:
                    break;
            }

        }
    });

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_activity_level_setting:
                finish();
                break;
            case R.id.bt_cancle_activity_level_setting:
                finish();
                break;
            case R.id.bt_select_activity_level_setting:
                Intent intent = new Intent();
                intent.putExtra("activity_level", mActivityLevel);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
