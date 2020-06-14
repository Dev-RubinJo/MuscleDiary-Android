package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class WeightActivity extends BaseActivity implements View.OnClickListener {

    private NumberPicker mIntPicker;
    private NumberPicker mFloatPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        //picker 생성함수
        createPickers();

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_weight);
        TextView btSelect = findViewById(R.id.bt_select_weight);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_weight_setting);
        btBack.setOnClickListener(this);
    }

    //picker 생성, 초기값 설정해주는 함수
    private void createPickers(){
        Intent intent = getIntent();
        double weight = intent.getDoubleExtra("weight", 0);

        //정수부분 picker
        mIntPicker = findViewById(R.id.picker_integer_weight_setting);
        mIntPicker.setMaxValue(300);
        mIntPicker.setMinValue(0);
        mIntPicker.setValue((int)weight);

        //소수점 부분 picker
        mFloatPicker = findViewById(R.id.picker_float_weight_setting);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setMinValue(0);
        mFloatPicker.setValue((int)(weight * 10.0 % 10.0));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_weight_setting:
                finish();
                break;
            case R.id.bt_cancle_weight:
                finish();
                break;
            case R.id.bt_select_weight:
                Intent intent = new Intent();
                double weight = (mIntPicker.getValue()*1.0) + (mFloatPicker.getValue() * 0.1);
                intent.putExtra("weight", weight);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
