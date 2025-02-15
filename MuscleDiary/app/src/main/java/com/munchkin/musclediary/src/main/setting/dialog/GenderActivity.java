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

public class GenderActivity extends BaseActivity implements View.OnClickListener {

    private int mGender;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        mGender = getIntent().getIntExtra("gender", 0);


        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radiogroup_gender_setting);
        radioGroup.setOnCheckedChangeListener(listener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_gender);
        TextView btSelect = findViewById(R.id.bt_select_gender);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_gender_setting);
        btBack.setOnClickListener(this);

        if(mGender == 1){
            radioGroup.check(R.id.radio_male_setting);
        } else if(mGender == 2){
            radioGroup.check(R.id.radio_female_setting);
        }
    }

    //라디오 그룹 리스너 설정
    private RadioGroup.OnCheckedChangeListener listener = (new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_male_setting:
                    mGender = 1;
                    break;

                case R.id.radio_female_setting:
                    mGender = 2;
                    break;

                default:
                    break;
            }

        }
    });

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_gender_setting:
                finish();
                break;
            case R.id.bt_cancle_gender:
                finish();
                break;
            case R.id.bt_select_gender:
                Intent intent = new Intent();
                intent.putExtra("gender", mGender);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
