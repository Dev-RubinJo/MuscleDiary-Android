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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AgeActivity extends BaseActivity implements View.OnClickListener {

    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;

    private NumberPicker mDayPicker;

    private TextView mTvAge;

    private String mDateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        //상단 현재 생년월일을 알려주는 TextView 생성, dateformat 생성, 적용
        mTvAge = findViewById(R.id.tv_age_setting);
        setDateFormat();

        //picker리스너 생성
        NumberPicker.OnValueChangeListener pickerListener = createPickerListener();

        //picker생성 함수
        createPickers(pickerListener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_age);
        TextView btSelect = findViewById(R.id.bt_select_age);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_age_setting);
        btBack.setOnClickListener(this);
    }

    //팝업창 열어서 dateformat만드는 함수
    //dateformat가 정확히 정해지면 수정필요
    private void setDateFormat(){
        Intent intent = getIntent();
        Calendar calendar = Calendar.getInstance();

        mYear = intent.getIntExtra("year", 2020);
        mMonth = intent.getIntExtra("month", 4);
        mDay = intent.getIntExtra("date", 26);
        calendar.set(mYear, mMonth-1, mDay);

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy년 MM월 dd일");
        mDateFormat = format.format(calendar.getTime());

        mTvAge.setText(mDateFormat);
    }

    private void createPickers(NumberPicker.OnValueChangeListener pickerListener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        //년도 선택 picker 설정(년도 설정 picker만 순환하지 않도록 한다.)
        NumberPicker yearPicker = findViewById(R.id.picker_year_age_setting);
        yearPicker.setMinValue(year - 200);
        yearPicker.setMaxValue(year);
        if(mYear != 0){
            yearPicker.setValue(mYear);
        }
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setOnValueChangedListener(pickerListener);

        //달 선택 picker 설정
        NumberPicker monthPicker = findViewById(R.id.picker_month_age_setting);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        if(mMonth != 0){
            monthPicker.setValue(mMonth);
        }
        monthPicker.setOnValueChangedListener(pickerListener);

        //일 선택 picker 설정
        mDayPicker = findViewById(R.id.picker_day_age_setting);
        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(30);
        if(mDay != 0){
            mDayPicker.setValue(mDay);
        }
        mDayPicker.setOnValueChangedListener(pickerListener);
    }

    //picker 변경 리스너 모음
    private NumberPicker.OnValueChangeListener createPickerListener(){
        NumberPicker.OnValueChangeListener listener = (new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                switch(picker.getId()){
                    case R.id.picker_year_age_setting:
                        mYear = newVal;
                        pickerChange(mDayPicker);
                        break;

                    case R.id.picker_month_age_setting:
                        mMonth = newVal;
                        pickerChange(mDayPicker);
                        break;

                    case R.id.picker_day_age_setting:
                        mDay = newVal;
                        pickerChange(picker);
                        break;

                    default:
                        break;
                }
            }
        });
        return listener;
    }

    //picker값 변경될 때마다 반영해주는 함수
    //상단 생년월일 mTvAge도 반영함
    private void pickerChange(NumberPicker numberPicker){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy년 MM월 dd일");
        int maxDate = 0;
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth-1);
        maxDate = calendar.getActualMaximum(Calendar.DATE);
        if(maxDate < mDay){
            mDay = maxDate;
        }
        calendar.set(Calendar.DATE, mDay);
        numberPicker.setMaxValue(maxDate);
        mDateFormat = format.format(calendar.getTime());
        mTvAge.setText(mDateFormat);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_age_setting:
                finish();
                break;
            case R.id.bt_cancle_age:
                finish();
                break;
            case R.id.bt_select_age:
                Intent intent = new Intent();
                intent.putExtra("age", mDateFormat);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
