package com.munchkin.musclediary.src.main.food;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.food.adapter.MealAdapter;
import com.munchkin.musclediary.src.main.food.models.MealItem;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FoodFragment extends BaseFragment implements View.OnClickListener, OnDateSelectedListener{

    private ArrayList<MealItem> mMealitems;
    private ArrayList<MenuItem> mMenuItems;
    private MealAdapter mMealAdapter;

    private final int WEB_PROTEIN = 0;

    private Button mbtWebProtein;

    //캘린더 설정, 날짜 형식 설정
    private MaterialCalendarView mCalendarView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_web_protein:
                Intent webproteinIntent = new Intent(getActivity(), WebProteinActivity.class);
                startActivity(webproteinIntent);
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_food, container, false);
        setComponentView(view);

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Log.d("life","onDestroyView enter");
    }

    // 생성될 음식 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        //더미데이터 생성
        addMealList();

        //웹뷰 보여주기 위한 설정
        mbtWebProtein = v.findViewById(R.id.bt_web_protein);
        mbtWebProtein.setOnClickListener(this);

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView mealRecyclerView = v.findViewById(R.id.fragment_food_rv_meal);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        mMealAdapter = new MealAdapter(getContext(),mMealitems);
        mealRecyclerView.setAdapter(mMealAdapter);

        //달력 xml과 연결
        mCalendarView = v.findViewById(R.id.fragment_food_calendarView);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setDateSelected(currentTime,true);
    }

    public void onCompleteMenuSelect(ArrayList<MenuItem> menuAddList, String mealTitle){

        for(int i=0; i<4; i++){
            if(mMealitems.get(i).getMealTitle().equals(mealTitle)){
                mMealitems.get(i).setMenuItemList(menuAddList);
            }
        }
        mMealAdapter.notifyDataSetChanged();
    }

    private void addMealList(){

        String titleList[] = {"아침","점심","저녁","기타"};
        double kcalList[] = {0.0,0.0,0.0,0.0};

        mMenuItems = new ArrayList<>();
        mMealitems = new ArrayList<>();

        for(int i = 0; i<titleList.length; i++){
            MealItem mealItem = new MealItem();
            mealItem.setMealTitle(titleList[i]);
            mealItem.setMealTotalCalories(kcalList[i]);
            mMealitems.add(mealItem);
        }

    }

    //캘린더
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String text = String.format("Date"+DATE_FORMAT.format(date.getDate()));
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
