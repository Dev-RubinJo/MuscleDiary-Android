package com.munchkin.musclediary.src.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.chart.ChartFragment;
import com.munchkin.musclediary.src.main.exercise.ExerciseFragment;
import com.munchkin.musclediary.src.main.food.FoodFragment;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.munchkin.musclediary.src.main.setting.SettingFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    //뷰페이져 어뎁터
    private MainViewPagerAdapter mAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPager = findViewById(R.id.container_main);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case 1000: {
                //액티비티로 전달된 객체를 다시 프레그먼트로 전송
                ArrayList<MenuItem> selectedMenu = new ArrayList<>();
                selectedMenu = (ArrayList<MenuItem>) data.getSerializableExtra("selectedMenu");
                String selectedMealTitle = data.getStringExtra("mealTitle");

                //프레그먼트 캐스팅
                FoodFragment foodFragment = (FoodFragment) mAdapter.getItem(0);
                foodFragment.onCompleteMenuSelect(selectedMenu,selectedMealTitle);
            }
        }
    }

    //텝레이아웃에 추가할 때 여기에 추가하는 함수
    public void setupViewPager(ViewPager viewPager) {
        mAdapter.addFragment(new FoodFragment(), "음식");
        mAdapter.addFragment(new ExerciseFragment(), "운동");
        mAdapter.addFragment(new ChartFragment(), "기록");
        mAdapter.addFragment(new SettingFragment(), "설정");
        viewPager.setAdapter(mAdapter);
    }
}
