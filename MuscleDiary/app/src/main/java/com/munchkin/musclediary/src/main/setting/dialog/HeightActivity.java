package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        TextView btCancle = findViewById(R.id.bt_cancle_height);
        TextView btSelect = findViewById(R.id.bt_select_height);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);
    }

    private void createPickers(){
        Intent intent = getIntent();
        int integer = intent.getIntExtra("integer", 0);
        int dot = intent.getIntExtra("dot", 0);

        //정수부분 picker
        mIntPicker = findViewById(R.id.picker_integer_height_setting);
        mIntPicker.setMaxValue(300);
        mIntPicker.setMinValue(0);
        mIntPicker.setValue(integer);

        //소수점 부분 picker
        mFloatPicker = findViewById(R.id.picker_float_height_setting);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setMinValue(0);
        mFloatPicker.setValue(dot);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_cancle_height:
                finish();
                break;
            case R.id.bt_select_height:
                Intent intent = new Intent();
                String height = mIntPicker.getValue() + "." + mFloatPicker.getValue() + "cm";
                intent.putExtra("height", height);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
