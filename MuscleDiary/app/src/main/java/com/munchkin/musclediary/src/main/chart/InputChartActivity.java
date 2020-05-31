package com.munchkin.musclediary.src.main.chart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private Button mBtAdd;
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
        mBtAdd = findViewById(R.id.input_chart_btn_complete);
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

        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Calendar c1 = Calendar.getInstance();
                int year = c1.get(Calendar.YEAR);
                String dayMonth = mDates[mDatePicker.getValue()];
                int month = Integer.parseInt(dayMonth.substring(0,2));
                int date = Integer.parseInt(dayMonth.substring(5,7));
                intent.putExtra("year", year) ;
                intent.putExtra("month", month);
                intent.putExtra("date", date);
                intent.putExtra("weight", mIntegerPicker.getValue() * 1.0f + mFloatPicker.getValue() * 0.1f);
                setResult(RESULT_OK, intent);
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