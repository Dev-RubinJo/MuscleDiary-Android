package com.munchkin.musclediary.src.main.exercise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.exercise.adapter.ExercisePartAdapter;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;
import com.munchkin.musclediary.src.main.exercise.models.ExercisePartItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class ExerciseFragment extends BaseFragment implements OnDateSelectedListener {

    private ArrayList<ExercisePartItem> mExercisePartItems;
    private ExercisePartAdapter mExercisePartAdapter;

    //캘린더 설정, 날짜 형식 설정
    private MaterialCalendarView mCalendarView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();
    SharedPreferences.Editor mEditor = sSharedPreferences.edit();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String todayText = String.format(DATE_FORMAT.format(currentTime));
        mEditor.putString("recordDateExercise", todayText);
        mEditor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 운동 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        //나중에 item 추가 할 수 있도록 기본설정
        initExerciseList();

        //운동리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView exerciseRecyclerView = v.findViewById(R.id.fragment_exercise_rv_exercise);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        mExercisePartAdapter = new ExercisePartAdapter(getContext(),mExercisePartItems);
        exerciseRecyclerView.setAdapter(mExercisePartAdapter);

        //달력 xml과 연결
        mCalendarView = v.findViewById(R.id.fragment_exercise_calendarView);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setDateSelected(currentTime,true);
    }

    public void onCompleteExerciseSelect(ExerciseItem exercise, String exercisePartTitle){
        for (int i=0; i<2; i++){
            if(mExercisePartItems.get(i).getExercisePartTitle().equals(exercisePartTitle)){
                mExercisePartItems.get(i).getExerciseItemList().add(exercise);
            }
        }
        mExercisePartAdapter.notifyDataSetChanged();
    }

    private void initExerciseList(){
        String titleList[] = {"근력운동", "유산소운동"};

        mExercisePartItems = new ArrayList<>();
        ArrayList<ExerciseItem> emptyExerciseListOne = new ArrayList<>();
        ArrayList<ExerciseItem> emptyExerciseListTwo = new ArrayList<>();

        //데이터 셋 준비
        for(int k = 0; k<2; k++){
            ExercisePartItem exercisePartItem = new ExercisePartItem();
            exercisePartItem.setExercisePartTitle(titleList[k]);
            mExercisePartItems.add(exercisePartItem);
            if(k==0){
                mExercisePartItems.get(k).setExerciseItemList(emptyExerciseListOne);
            }else{
                mExercisePartItems.get(k).setExerciseItemList(emptyExerciseListTwo);
            }

        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String selectedDay = String.format(DATE_FORMAT.format(date.getDate()));
        mEditor.putString("recordDateExercise", selectedDay);
        mEditor.apply();
    }
}
