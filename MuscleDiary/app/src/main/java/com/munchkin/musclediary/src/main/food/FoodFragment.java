package com.munchkin.musclediary.src.main.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.food.adapter.MealAdapter;
import com.munchkin.musclediary.src.main.food.models.MealItem;

import java.util.ArrayList;

public class FoodFragment extends BaseFragment {

    private ArrayList<MealItem> mMealitems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_food, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 음식 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        //더미데이터 생성
        addMealList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView mealRecyclerView = v.findViewById(R.id.fragment_food_rv_meal);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        MealAdapter mealAdapter = new MealAdapter(getContext(),mMealitems);
        mealRecyclerView.setAdapter(mealAdapter);

    }

    private void addMealList(){
        //default 아침, 점심, 저녁
        String titleList[] = {"아침","점심","저녁"};
        double kcalList[] = {434.7,220.6,324.5};
        mMealitems = new ArrayList<>();

        for(int i = 0; i<titleList.length; i++){
            MealItem mealItem = new MealItem();
            mealItem.setMealTitle(titleList[i]);
            mealItem.setMealTotalCalories(kcalList[i]);
            mMealitems.add(mealItem);
        }
    }
}
