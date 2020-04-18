package com.munchkin.musclediary.src.main.chart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class InputChartActivity extends BaseActivity {
    private Button mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_chart);
        //컴포넌트 연결
        mBtnBack = findViewById(R.id.input_chart_btn_cancle);

        //listener 등록
        this.initOnClickListener();
    }

    private void initOnClickListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
