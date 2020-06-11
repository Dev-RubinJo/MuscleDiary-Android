package com.munchkin.musclediary.src.main.exercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

public class ArrangeExerciseActivity extends BaseActivity {
    //사용할 객체들 가져오기
    private EditText mEtWeight;
    private NumberPicker mRepeatPicker;
    private NumberPicker mSetPicker;

    private TextView mBtnCancle;
    private TextView mBtnSelect;

    private TextView mTvExerciseName;

    private Button mBtnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_exercise);

        /* content 연결
        exercise 이름 지정 */

        mTvExerciseName = findViewById(R.id.arg_exercise_tv_exercise_name);
        Intent getIntent = getIntent();
        String exercise = getIntent.getStringExtra("exerciseName");
        mTvExerciseName.setText(exercise);
        //TODO: InputExerciseActivity 에서 intent로 보내기

        //picker 생성함수
        createPickers();

        //취소, 완료 버튼 설정
        mBtnCancle = findViewById(R.id.arg_exercise_btn_cancle);
        mBtnSelect = findViewById(R.id.arg_exercise_btn_select);

        //배경 클릭 설정
        mBtnBack = findViewById(R.id.arg_exercise_btn_back);

        //클릭 리스너 등록
        initListener();
    }

    private void createPickers() {
        //중량 입력
        mEtWeight = findViewById(R.id.arg_exercise_et_weight);

        //횟수 입력
        mRepeatPicker = findViewById(R.id.arg_exercise_picker_repeat);
        mRepeatPicker.setMaxValue(200);
        mRepeatPicker.setMinValue(1);
        mRepeatPicker.setValue(1);
        mRepeatPicker.setWrapSelectorWheel(false);

        //세트 입력
        mSetPicker = findViewById(R.id.arg_exercise_picker_set);
        mSetPicker.setMaxValue(50);
        mSetPicker.setMinValue(1);
        mSetPicker.setValue(1);
        mRepeatPicker.setWrapSelectorWheel(false);
    }

    private void initListener() {
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToInputActivity = new Intent();
                backToInputActivity.putExtra("weight",-1.0);
                backToInputActivity.putExtra("repeat",-1);
                backToInputActivity.putExtra("set",-1);
                setResult(Activity.RESULT_OK,backToInputActivity);
                finish();
            }
        });

        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToInputActivity = new Intent();
                double weight;
                String textInput = mEtWeight.getText().toString();
                if(!textInput.isEmpty()){
                    try{
                        weight = Double.parseDouble(textInput);
                        backToInputActivity.putExtra("weight",weight);
                        backToInputActivity.putExtra("repeat",mRepeatPicker.getValue());
                        backToInputActivity.putExtra("set",mSetPicker.getValue());
                        setResult(Activity.RESULT_OK,backToInputActivity);
                    } catch (Exception e1) {
                        Toast.makeText(getApplicationContext(),"잘못된 무게 값을 입력하셨습니다.",Toast.LENGTH_SHORT);
                    }
                }
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
