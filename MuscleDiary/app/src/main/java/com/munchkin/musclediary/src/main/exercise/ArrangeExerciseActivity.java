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
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

public class ArrangeExerciseActivity extends BaseActivity {
    //사용할 객체들 가져오기
    private EditText mEtMinOrWeight;
    private NumberPicker mIntensityOrRepeatPicker;
    private NumberPicker mSetPicker;

    private TextView mBtnCancle;
    private TextView mBtnSelect;
    private TextView mTvExercisePartName;

    private TextView mTvMinOrWeight;
    private TextView mTvIntensityOrRepeat;

    private EditText mEtExerciseName;

    private Button mBtnBack;
    private String mExercisePart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_exercise);

        /* content 연결 */
        mTvExercisePartName = findViewById(R.id.arg_exercise_tv_exercise_part_name);
        Intent getIntent = getIntent();
        mExercisePart = getIntent.getStringExtra("exercisePartTitle");
        String exercisePart = mExercisePart + " 추가";
        mTvExercisePartName.setText(exercisePart);

        //단위 표시할 녀석들
        mTvMinOrWeight = findViewById(R.id.arg_exercise_tv_min_or_weight);
        mTvIntensityOrRepeat = findViewById(R.id.arg_exercise_tv_intensity_or_repeat);
        mEtMinOrWeight = findViewById(R.id.arg_exercise_et_min_or_weight);

        if(mExercisePart.equals("근력운동")){
            mTvMinOrWeight.setText("kg");
            mTvIntensityOrRepeat.setText("회");
            mEtMinOrWeight.setHint("중량입력");
        }else{
            mTvMinOrWeight.setText("분");
            mTvIntensityOrRepeat.setText("강도");
            mEtMinOrWeight.setHint("시간입력");
        }

        //mEtExerciseName 연결
        mEtExerciseName = findViewById(R.id.arg_exercise_et_exercise_name);

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
        //횟수 입력
        mIntensityOrRepeatPicker = findViewById(R.id.arg_exercise_picker_repeat);
        mIntensityOrRepeatPicker.setMaxValue(200);
        mIntensityOrRepeatPicker.setMinValue(1);
        mIntensityOrRepeatPicker.setValue(1);
        mIntensityOrRepeatPicker.setWrapSelectorWheel(false);

        //세트 입력
        mSetPicker = findViewById(R.id.arg_exercise_picker_set);
        mSetPicker.setMaxValue(50);
        mSetPicker.setMinValue(1);
        mSetPicker.setValue(1);
        mIntensityOrRepeatPicker.setWrapSelectorWheel(false);
    }

    private void initListener() {
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainActivity = new Intent();
                double weight;
                int time;
                String minOrWeightInput = mEtMinOrWeight.getText().toString();
                String exerciseNameInput = mEtExerciseName.getText().toString();

                //근력운동일때
                if(mExercisePart.equals("근력운동")){
                    if(!minOrWeightInput.isEmpty() && !exerciseNameInput.isEmpty()){
                        try{
                            weight = Double.parseDouble(minOrWeightInput);

                            ExerciseItem exerciseItem = new ExerciseItem(exerciseNameInput,mExercisePart);
                            exerciseItem.setSet(mSetPicker.getValue());
                            exerciseItem.setRepeat(mIntensityOrRepeatPicker.getValue());
                            exerciseItem.setWeight(weight);

                            backToMainActivity.putExtra("addExercise",exerciseItem);
                            setResult(Activity.RESULT_OK,backToMainActivity);
                            finish();
                        } catch (Exception e1) {
                            Toast.makeText(getApplicationContext(),"잘못된 중량 값을 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"빈칸을 모두 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //유산소 운동일때
                    if(!minOrWeightInput.isEmpty() && !exerciseNameInput.isEmpty()){
                        try{
                            time = Integer.parseInt(minOrWeightInput);

                            ExerciseItem exerciseItem = new ExerciseItem(exerciseNameInput,mExercisePart);
                            exerciseItem.setSet(mSetPicker.getValue());
                            exerciseItem.setIntensity(mIntensityOrRepeatPicker.getValue());
                            exerciseItem.setMin(time);

                            backToMainActivity.putExtra("addExercise",exerciseItem);
                            setResult(Activity.RESULT_OK,backToMainActivity);
                            finish();
                        } catch (Exception e1) {
                            Toast.makeText(getApplicationContext(),"잘못된 시간 값을 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"빈칸을 모두 입력해주세요",Toast.LENGTH_SHORT).show();
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
