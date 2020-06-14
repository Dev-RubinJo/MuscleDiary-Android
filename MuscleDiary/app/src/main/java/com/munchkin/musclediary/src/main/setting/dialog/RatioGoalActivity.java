package com.munchkin.musclediary.src.main.setting.dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class RatioGoalActivity extends BaseActivity implements View.OnClickListener {

    //상단 %
    private TextView mTvRatioGoal;

    //비율별 그램
    private TextView mTvCarbohydrate;
    private TextView mTvProtein;
    private TextView mTvFat;

    //Picker생성
    private NumberPicker mCarbohydratePicker;
    private NumberPicker mProteinPicker;
    private NumberPicker mFatPicker;

    //비율 저장할 배열
    private int[] mRateoArray = {0,0,0};

    private boolean mIsOk = false;

    //그램수 계산을 위한 shared preference
    SharedPreferences.Editor mEditor = sSharedPreferences.edit();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratio_goal);

        //상단 100% 표시(100%가 아닐때는 완료버튼 비활성화
        mTvRatioGoal = findViewById(R.id.tv_ratio_goal);

        //그램수 표시
        mTvCarbohydrate = findViewById(R.id.tv_gram_carbohydrate);
        mTvProtein = findViewById(R.id.tv_gram_protein);
        mTvFat = findViewById(R.id.tv_gram_fat);

        //picker리스너 생성
        NumberPicker.OnValueChangeListener pickerListener = createPickerListener();

        //picker생성 함수
        createPickers(pickerListener);

        //취소, 완료 버튼 설정
        TextView btCancle = findViewById(R.id.bt_cancle_ratio_goal);
        TextView btSelect = findViewById(R.id.bt_select_ratio_goal);

        btCancle.setOnClickListener(this);
        btSelect.setOnClickListener(this);

        //배경 클릭 설정
        Button btBack = findViewById(R.id.bt_back_ratio_goal);
        btBack.setOnClickListener(this);
    }


    private void createPickers(NumberPicker.OnValueChangeListener pickerListener){
        int maxvalue = 100;
        int minvalue = 0;
        int step = 5;
        String[] valueSet = new String[21];
        //
        for(int i = 0; i <= 20; i++) {
            valueSet[i] = Integer.toString(i*5);
        }

        //년도 선택 picker 설정(년도 설정 picker만 순환하지 않도록 한다.)
        mCarbohydratePicker = findViewById(R.id.picker_carbohydrate_ratio_goal);
        int carbohydrate = (getIntent().getIntExtra("carbohydrate", 0))/5;
        mCarbohydratePicker.setOnValueChangedListener(pickerListener);
        mCarbohydratePicker.setMinValue(0);
        mCarbohydratePicker.setMaxValue((maxvalue - minvalue) / step);
        mCarbohydratePicker.setDisplayedValues(valueSet);
        mCarbohydratePicker.setValue(carbohydrate);

        //달 선택 picker 설정
        mProteinPicker = findViewById(R.id.picker_protein_ratio_goal);
        int protein = (getIntent().getIntExtra("protein", 0))/5;
        mProteinPicker.setOnValueChangedListener(pickerListener);
        mProteinPicker.setMinValue(0);
        mProteinPicker.setMaxValue((maxvalue - minvalue) / step);
        mProteinPicker.setDisplayedValues(valueSet);
        mProteinPicker.setValue(protein);

        //일 선택 picker 설정
        mFatPicker = findViewById(R.id.picker_fat_ratio_goal);

        int fat = (getIntent().getIntExtra("fat", 0))/5;
        mFatPicker.setOnValueChangedListener(pickerListener);
        mFatPicker.setMinValue(0);
        mFatPicker.setMaxValue((maxvalue - minvalue) / step);
        mFatPicker.setDisplayedValues(valueSet);
        mFatPicker.setValue(fat);

        changePercent();
        changeGram(carbohydrate, mTvCarbohydrate, 4);
        changeGram(protein, mTvProtein, 4);
        changeGram(fat, mTvFat, 9);
    }

    //picker 변경 리스너 모음
    private NumberPicker.OnValueChangeListener createPickerListener(){
        NumberPicker.OnValueChangeListener listener = (new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch(picker.getId()){
                    case R.id.picker_carbohydrate_ratio_goal:
                        //탄수화물 변경할때 그램수 변경
                        int carbohydrate = picker.getValue();
                        changeGram(carbohydrate, mTvCarbohydrate, 4);
                        break;

                    case R.id.picker_protein_ratio_goal:
                        //단백질 변경할때 그램수 변경
                        int protein = picker.getValue();
                        changeGram(protein, mTvProtein, 4);
                        break;

                    case R.id.picker_fat_ratio_goal:
                        //지방 변경할때 그램수 변경
                        int fat = picker.getValue();
                        changeGram(fat, mTvFat, 9);
                        break;

                    default:
                        break;
                }
                changePercent();
            }
        });
        return listener;
    }

    //그램 변경하는 함수
    private void changeGram(int ratio, TextView textView, int gramRatio){
        int totalKcal = getIntent().getIntExtra("kcal", 0);
        int kcal = totalKcal * ratio * 5 / 100;
        String gram = kcal/gramRatio + "g";
        textView.setText(gram);
    }

    //그램 shared에 등록하는 함수
    private int getFinalGram(int ratio, int gramRatio){
        int totalKcal = getIntent().getIntExtra("kcal", 0);
        int kcal = totalKcal * ratio  / 100;
        int gram = kcal/gramRatio;
        return gram;
    }

    //바뀔때마다 %변경해주는 함수(임시 배열에 저장한다)
    private void changePercent(){
        int carbohydrate = mCarbohydratePicker.getValue()*5;
        int protein = mProteinPicker.getValue()*5;
        int fat = mFatPicker.getValue()*5;
        mRateoArray[0] = carbohydrate;
        mRateoArray[1] = protein;
        mRateoArray[2] = fat;
        int total = carbohydrate + protein + fat;
        String percent = total + "%";
        mTvRatioGoal.setText(percent);
        if(total != 100){
            mIsOk = false;
            mTvRatioGoal.setTextColor(Color.RED);
        } else{
            mIsOk = true;
            mTvRatioGoal.setTextColor(Color.rgb(71,200,62));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_back_ratio_goal:
                finish();
                break;
            case R.id.bt_cancle_ratio_goal:
                finish();
                break;
            case R.id.bt_select_ratio_goal:
                if(mIsOk){
                    Intent intent = new Intent();
                    intent.putExtra("carbohydrate", mRateoArray[0]);
                    intent.putExtra("protein", mRateoArray[1]);
                    intent.putExtra("fat", mRateoArray[2]);
                    mEditor.putInt("carbohydrateGram",getFinalGram(mRateoArray[0],4));
                    mEditor.putInt("proteinGram",getFinalGram(mRateoArray[1],4));
                    mEditor.putInt("fatGram",getFinalGram(mRateoArray[2],9));
                    mEditor.commit();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showCustomToast("100%가 아닙니다");
                }
                break;
            default:
                break;
        }
    }
}
