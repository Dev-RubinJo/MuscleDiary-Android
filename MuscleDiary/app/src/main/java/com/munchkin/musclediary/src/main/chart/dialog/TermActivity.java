package com.munchkin.musclediary.src.main.chart.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class TermActivity extends BaseActivity implements View.OnClickListener {

    private int mTerm = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);


        //라디오 그룹 설정
        RadioGroup radioGroup = findViewById(R.id.radiogroup_term_chart);
        radioGroup.setOnCheckedChangeListener(listener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_term);
        TextView btSelect = findViewById(R.id.bt_select_term);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_term_chart);
        btBack.setOnClickListener(this);
    }

    //라디오 그룹 리스너 설정
    private RadioGroup.OnCheckedChangeListener listener = (new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.radio_year_chart:
                    mTerm = 2;
                    break;

                case R.id.radio_month_chart:
                    mTerm = 1;
                    break;

                case R.id.radio_date_chart:
                    mTerm = 0;
                    break;

                default:
                    break;
            }

        }
    });

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_term_chart:
                finish();
                break;
            case R.id.bt_cancle_term:
                finish();
                break;
            case R.id.bt_select_term:
                Intent intent = new Intent();
                intent.putExtra("term", mTerm);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
