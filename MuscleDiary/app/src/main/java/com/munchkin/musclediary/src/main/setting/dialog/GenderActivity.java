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

    private String mGender;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        mGender = getIntent().getStringExtra("gender");


        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radiogroup_gender_setting);
        radioGroup.setOnCheckedChangeListener(listener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_gender);
        TextView btSelect = findViewById(R.id.bt_select_gender);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);
    }

    private RadioGroup.OnCheckedChangeListener listener = (new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_male_setting:
                    mGender = "남성";
                    break;

                case R.id.radio_female_setting:
                    mGender = "여성";
                    break;

                default:
                    break;
            }

        }
    });

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
