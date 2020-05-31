package com.munchkin.musclediary.src.main.food;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.food.adapter.MealAdapter;
import com.munchkin.musclediary.src.main.food.models.MealItem;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class FoodFragment extends BaseFragment {

    private ArrayList<MealItem> mMealitems;
    private ArrayList<MenuItem> mMenuItems;
    private MealAdapter mMealAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_food, container, false);
        setComponentView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMenuItems.clear();
        mMealAdapter.notifyDataSetChanged();
        Log.d("jooan","onResume enter");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d("jooan","onDestroyView enter");
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
        mMealAdapter = new MealAdapter(getContext(),mMealitems);
        mealRecyclerView.setAdapter(mMealAdapter);

    }

    private void addMealList(){
        //default 아침, 점심, 저녁
        String titleList[] = {"아침","점심","저녁"};
        double kcalList[] = {434.7,220.6,324.5};

        String menutitleList[] = {"계란밥","된장국","스파케티","사과주스"};
        double menuKcalList[] = {50,12,102,124.2,24,13,25,354.3,113,13,112,113.4,23,24,25,231.4};

        mMenuItems = new ArrayList<>();
        mMealitems = new ArrayList<>();


        //아침메뉴용 더미데이터
        for(int j =0; j<4; j++){
            MenuItem menuItem = new MenuItem(menutitleList[j],menuKcalList[4*j],menuKcalList[(4*j)+1],
                    menuKcalList[(4*j)+2],menuKcalList[(4*j)+3]);
            mMenuItems.add(menuItem);
        }

        for(int i = 0; i<titleList.length; i++){
            MealItem mealItem = new MealItem();
            mealItem.setMealTitle(titleList[i]);
            mealItem.setMealTotalCalories(kcalList[i]);
            mMealitems.add(mealItem);
        }

        mMealitems.get(0).setMenuItemList(mMenuItems);
    }
}
