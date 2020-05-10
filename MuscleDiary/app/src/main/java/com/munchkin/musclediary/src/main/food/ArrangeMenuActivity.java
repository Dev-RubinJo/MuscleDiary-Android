package com.munchkin.musclediary.src.main.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class ArrangeMenuActivity extends BaseActivity {

    private NumberPicker mIntPicker;
    private NumberPicker mFloatPicker;

    private TextView mBtnCancle;
    private TextView mBtnSelect;

    private TextView mTvMenuName;
    private TextView mTvMenuDescription;

    private Button mBtnBack;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_arrange_menu);

        //menu 이름 지정
        mTvMenuName = findViewById(R.id.arg_menu_tv_menu_name);
        Intent getIntent = getIntent();
        String menu = getIntent.getStringExtra("menuName");
        mTvMenuName.setText(menu);

        //picker 생성함수
        createPickers();

        //취소, 완료 버튼 설정
        mBtnCancle = findViewById(R.id.arg_menu_btn_cancle);
        mBtnSelect = findViewById(R.id.arg_menu_btn_select);

        //배경 클릭 설정
        mBtnBack = findViewById(R.id.arg_menu_btn_back);

        initListener();

    }

    //picker 생성, 초기값 설정해주는 함수
    private void createPickers(){

        //정수부분 picker
        mIntPicker = findViewById(R.id.arg_menu_picker_integer);
        mIntPicker.setMaxValue(10);
        mIntPicker.setMinValue(0);
        mIntPicker.setValue(1);
        mIntPicker.setWrapSelectorWheel(false);

        //소수점 부분 picker
        mFloatPicker = findViewById(R.id.arg_menu_picker_float);
        mFloatPicker.setMaxValue(9);
        mFloatPicker.setMinValue(0);
        mFloatPicker.setValue(0);
    }

    private void initListener(){

        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "추가되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
