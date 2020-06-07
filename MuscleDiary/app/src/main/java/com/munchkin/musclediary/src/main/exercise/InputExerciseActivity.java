package com.munchkin.musclediary.src.main.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

import java.util.ArrayList;

public class InputExerciseActivity extends BaseActivity {

    String mExerciseTitle;
    TextView mTvExerciseTitle;
    RecyclerView mExerciseResultRecyclerView;
    //ExerciseResultAdapter mExerciseResultAdapter;

    //선택한 운동들 보여주는 리사이클러뷰
    RecyclerView mSelectedExerciseRecyclerView;
    //SelectedExerciseAdapter mSelectedExerciseAdapter;

    //선택한 운동을 담을 리스트
    ArrayList<ExerciseItem> mClickedExerciseIteml;

    //검색창과 검색 결과를 담을 리스트
    EditText mEtExerciseSearch;
    private ArrayList<ExerciseItem> mExerciseItems;

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
}
