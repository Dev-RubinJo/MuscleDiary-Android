package com.munchkin.musclediary.src.main.chart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.google.firebase.analytics.FirebaseAnalytics;
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
    private DatePicker mDatePicker;
    private NumberPicker mIntegerPicker;
    private NumberPicker mFloatPicker;

    private int mYear = 0;
    private int mMonth = 0;
    private int mDate = 0;

    private String mDates[];

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chart);
        //컴포넌트 연결
        mBtnBack = findViewById(R.id.input_chart_btn_cancle);
        mBtAdd = findViewById(R.id.input_chart_btn_complete);
        mDatePicker = (DatePicker) findViewById(R.id.date_picker_input_chart);
        mIntegerPicker = (NumberPicker) findViewById(R.id.input_chart_integer_picker);
        mFloatPicker = (NumberPicker) findViewById(R.id.input_chart_float_picker);

        //현재 날짜 설정
        setDate();

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
                mYear = mDatePicker.getYear();
                mMonth = mDatePicker.getMonth() +1;
                mDate = mDatePicker.getDayOfMonth();
                intent.putExtra("year", mYear) ;
                intent.putExtra("month", mMonth);
                intent.putExtra("date", mDate);
                intent.putExtra("weight", mIntegerPicker.getValue() * 1.0f + mFloatPicker.getValue() * 0.1f);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initNumberPicker() {
        mDatePicker.init(mYear, mMonth-1, mDate, mOnDateChangedListener);
        mDatePicker.setMaxDate(System.currentTimeMillis());

        mIntegerPicker.setMinValue(0);
        mIntegerPicker.setMaxValue(120);
        mIntegerPicker.setWrapSelectorWheel(false);
        mIntegerPicker.setOnLongPressUpdateInterval(100);
        mIntegerPicker.setValue(30);

        mFloatPicker.setMinValue(0);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setWrapSelectorWheel(true);
        mFloatPicker.setOnLongPressUpdateInterval(100);
    }

    private DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener(){
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDate = dd;
        }
    };

    private void setDate(){
        Calendar c1 = Calendar.getInstance();
        mYear = c1.get(Calendar.YEAR);
        mMonth = c1.get(Calendar.MONTH) + 1;
        mDate = c1.get(Calendar.DATE);
    }

    private String[] getDatesFromCalender() {
        Calendar c1 = Calendar.getInstance();
        mYear = c1.get(Calendar.YEAR);
        mMonth = c1.get(Calendar.MONTH) + 1;
        mDate = c1.get(Calendar.DATE);

        List<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("MM / dd (EE)");
        dates.add(dateFormat.format(c1.getTime()));


        for (int i = 0; i < 2000; i++) {
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