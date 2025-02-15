package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class HeightActivity extends BaseActivity implements View.OnClickListener {

    private NumberPicker mIntPicker;
    private NumberPicker mFloatPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        //picker 생성함수
        createPickers();

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_height_setting);
        TextView btSelect = findViewById(R.id.bt_select_height_setting);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_height_setting);
        btBack.setOnClickListener(this);
    }

    //picker 생성, 초기값 설정해주는 함수
    private void createPickers(){
        Intent intent = getIntent();

        double height = intent.getDoubleExtra("height", 160);

        //정수부분 picker
        mIntPicker = findViewById(R.id.picker_integer_height_setting);
        mIntPicker.setMaxValue(300);
        mIntPicker.setMinValue(0);
        mIntPicker.setValue((int)height);

        //소수점 부분 picker
        mFloatPicker = findViewById(R.id.picker_float_height_setting);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setMinValue(0);
        mFloatPicker.setValue((int)(height * 10.0 % 10.0));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_height_setting:
                finish();
                break;
            case R.id.bt_cancle_height_setting:
                finish();
                break;
            case R.id.bt_select_height_setting:
                Intent intent = new Intent();
                double height = (mIntPicker.getValue()*1.0) + (mFloatPicker.getValue() * 0.1);
                intent.putExtra("height", height);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
