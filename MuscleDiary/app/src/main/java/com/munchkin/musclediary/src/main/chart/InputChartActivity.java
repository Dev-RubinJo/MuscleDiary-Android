package com.munchkin.musclediary.src.main.chart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.NumberPicker;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InputChartActivity extends BaseActivity {
    private Button mBtnBack;
    private NumberPicker mDatePicker;
    private NumberPicker mIntegerPicker;
    private NumberPicker mFloatPicker;

    private String mDates[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chart);
        //컴포넌트 연결
        mBtnBack = findViewById(R.id.input_chart_btn_cancle);
        mDatePicker = (NumberPicker) findViewById(R.id.input_chart_date_picker);
        mIntegerPicker = (NumberPicker) findViewById(R.id.input_chart_integer_picker);
        mFloatPicker = (NumberPicker) findViewById(R.id.input_chart_float_picker);

        //listener 등록
        this.initOnClickListener();
        //number picker 값 등록
        mDates = getDatesFromCalender();
        this.initNumberPicker();
    }

    private void initOnClickListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initNumberPicker() {
        mDatePicker.setMinValue(0);
        mDatePicker.setMaxValue(mDates.length-1);
        mDatePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return mDates[value];
            }
        });
        mDatePicker.setWrapSelectorWheel(false);
        mDatePicker.setOnLongPressUpdateInterval(100);
        mDatePicker.setDisplayedValues(mDates);

        mIntegerPicker.setMinValue(0);
        mIntegerPicker.setMaxValue(120);
        mIntegerPicker.setWrapSelectorWheel(false);
        mIntegerPicker.setOnLongPressUpdateInterval(100);
        mIntegerPicker.setValue(70);

        mFloatPicker.setMinValue(0);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setWrapSelectorWheel(true);
        mFloatPicker.setOnLongPressUpdateInterval(100);
    }

    private String[] getDatesFromCalender() {
        Calendar c1 = Calendar.getInstance();

        List<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("MM / dd (EE)");
        dates.add(dateFormat.format(c1.getTime()));


        for (int i = 0; i < 60; i++) {
            c1.add(Calendar.DATE, -1);
            dates.add(dateFormat.format(c1.getTime()));
        }

//      미래까지 필요시
//        for (int i = 0; i < 60; i++) {
//            c1.add(Calendar.DATE, 1);
//            dates.add(dateFormat.format(c1.getTime()));
//        }
//        c2.add(Calendar.DATE, -60);


        return dates.toArray(new String[dates.size() - 1]);
    }
}