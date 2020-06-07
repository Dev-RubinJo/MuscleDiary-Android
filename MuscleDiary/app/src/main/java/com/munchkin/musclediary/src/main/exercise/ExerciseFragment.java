package com.munchkin.musclediary.src.main.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class ExerciseFragment extends BaseFragment implements OnDateSelectedListener {

    private ArrayList<ExercisePartItem> mExercisePartItems;
    private ArrayList<ExerciseItem> mExerciseItems;
    private ArrayList<ExerciseItem> mExerciseItems2;

    //캘린더 설정, 날짜 형식 설정
    private MaterialCalendarView mCalendarView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 운동 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        //더미데이터 생성
        addExerciseList();

        //운동리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView exerciseRecyclerView = v.findViewById(R.id.fragment_exercise_rv_exercise);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        ExercisePartAdapter exercisePartAdapter = new ExercisePartAdapter(getContext(),mExercisePartItems);
        exerciseRecyclerView.setAdapter(exercisePartAdapter);

        //달력 xml과 연결
        mCalendarView = v.findViewById(R.id.fragment_exercise_calendarView);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setDateSelected(currentTime,true);
    }

    private void addExerciseList(){
        String titleList[] = {"근력운동", "유산소운동"};

        String exerciseList[] = {"풀업","친업","체어딥스","버터플라이"};
        String description[] = {"5(lap) / 3(set)","15(lap) / 2(set)","18(lap) / 3(set)","15(lap) / 5(set)"};

        String exerciseList2[] = {"런닝머신","사이클","스피닝"};
        String description2[] = {"15(min) / 5(intensity)","15(min) / 5(intensity)","15(min) / 5(intensity)"};

        mExerciseItems = new ArrayList<>();
        mExerciseItems2 = new ArrayList<>();
        mExercisePartItems = new ArrayList<>();

        //상체용 더미데이터
        for(int i = 0; i<4; i++){
            ExerciseItem exerciseItem = new ExerciseItem(exerciseList[i],description[i]);
            mExerciseItems.add(exerciseItem);
        }

        //하체용 더미데이터
        for(int j = 0; j<3; j++){
            ExerciseItem exerciseItem2 = new ExerciseItem(exerciseList2[j],description2[j]);
            mExerciseItems2.add(exerciseItem2);
        }

        for(int k = 0; k<2; k++){
            ExercisePartItem exercisePartItem = new ExercisePartItem();
            exercisePartItem.setExercisePartTitle(titleList[k]);
            mExercisePartItems.add(exercisePartItem);
        }

        mExercisePartItems.get(0).setExerciseItemList(mExerciseItems);
        mExercisePartItems.get(1).setExerciseItemList(mExerciseItems2);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String text = String.format("Date"+DATE_FORMAT.format(date.getDate()));
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
