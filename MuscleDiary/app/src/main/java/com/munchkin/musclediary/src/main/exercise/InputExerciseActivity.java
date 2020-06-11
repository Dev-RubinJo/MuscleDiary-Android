package com.munchkin.musclediary.src.main.exercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.exercise.adapter.ExerciseResultAdapter;
import com.munchkin.musclediary.src.main.exercise.adapter.SelectedExerciseAdapter;
import com.munchkin.musclediary.src.main.exercise.interfaces.ResultExerciseItemClickListener;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

import java.util.ArrayList;

public class InputExerciseActivity extends BaseActivity implements View.OnClickListener, ResultExerciseItemClickListener {

    String mExerciseTitle;
    TextView mTvExerciseTitle;
    RecyclerView mExerciseResultRecyclerView;
    ExerciseResultAdapter mExerciseResultAdapter;

    //선택한 운동들 보여주는 리사이클러뷰
    RecyclerView mSelectedExerciseRecyclerView;
    SelectedExerciseAdapter mSelectedExerciseAdapter;

    //선택한 운동을 담을 리스트
    ArrayList<ExerciseItem> mClickedExerciseItem;
    ExerciseItem mSelectedItem;

    //검색창과 검색 결과를 담을 리스트
    EditText mEtExerciseSearch;
    private ArrayList<ExerciseItem> mExerciseItems;

    ResultExerciseItemClickListener mResultExerciseItemClickListener = new ResultExerciseItemClickListener() {
        @Override
        public void onResultItemClicked(Intent intentSending, ExerciseItem clickedItem) {
            mSelectedItem = clickedItem;
            startActivityForResult(intentSending,4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_exercise);

        //레이아웃 컴포넌트 설정
        mTvExerciseTitle = findViewById(R.id.input_exercise_tv_exercise_title);
        mExerciseResultRecyclerView = findViewById(R.id.input_exercise_rv_exercise_result);
        mSelectedExerciseRecyclerView = findViewById(R.id.input_exercise_rv_exercise_add_list);

        mExerciseItems = new ArrayList<>();
        //검색창 생성
        mEtExerciseSearch = findViewById(R.id.input_exercise_et_search);
        //키보드에 있는 검색버튼 클릭 적용
        addEditiorActionListener();

        //인텐트 받아오기 (운동 제목)
        Intent getIntent = getIntent();
        mExerciseTitle = getIntent.getStringExtra("exerciseTitle");

        //제목 바꾸기
        mTvExerciseTitle.setText(mExerciseTitle);

        //리사이클러뷰 레이아웃 매니저 적용
        mExerciseResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mExerciseResultAdapter = new ExerciseResultAdapter(getApplicationContext(),mExerciseItems,mResultExerciseItemClickListener);
        mExerciseResultRecyclerView.setAdapter(mExerciseResultAdapter);

        //추가 메뉴 리사이클러뷰 레이아웃 매니저 적용
        mSelectedExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        mSelectedExerciseAdapter = new SelectedExerciseAdapter(getApplicationContext(), mClickedExerciseItem);
        mSelectedExerciseRecyclerView.setAdapter(mSelectedExerciseAdapter);

        //검색버튼 생성, 클릭이벤트 적용
        ImageButton btSearch = findViewById(R.id.input_exercise_btn_search);
        Button btComplete = findViewById(R.id.input_exercise_btn_complete);
        btSearch.setOnClickListener(this);
        btComplete.setOnClickListener(this);

    }

    private void clearExerciseList(){
        mExerciseItems.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case 4000: {
                //입력완료 -1 인지 검사
                int repeat = data.getIntExtra("repeat",1);
                int set = data.getIntExtra("set",1);
                double weight = data.getDoubleExtra("weight",1);

                //취소
                if(repeat != -1){
                    mSelectedItem.setRepeat(repeat);
                    mSelectedItem.setSet(set);
                    mSelectedItem.setWeight(weight);
                }else{
                    return;
                }
            }
        }
    }

    private void addEditiorActionListener() {
        mEtExerciseSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String menu = mEtExerciseSearch.getText().toString();
                    Log.d("test", Integer.toString(menu.length()));
                    if(menu.length() == 0){
                        //clearExerciseList();
                        //mExerciseResultAdapter.notifyDataSetChanged();
                    } else {
                        //tryGetExerciseList(menu);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_exercise_btn_search:
                //검색버튼 누를 때 editText에 있는 검색어로 api연결
                String menu = mEtExerciseSearch.getText().toString();
                if(menu.length() == 0) {
                    clearExerciseList();
                    //mExerciseResultAdapter.notifyDataSetChanged();
                } else
                    //TODO tryGetFoodList(menu);
                break;

            case R.id.input_exercise_btn_complete:
                Intent backToMainActivity = new Intent();
                backToMainActivity.putExtra("selectedExercise",mClickedExerciseItem);
                backToMainActivity.putExtra("mealTitle",mExerciseTitle);
                setResult(Activity.RESULT_OK,backToMainActivity);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResultItemClicked(Intent intentSending, ExerciseItem clickedItem) {
        mClickedExerciseItem.add(clickedItem);
        startActivityForResult(intentSending,4000);
    }
}
