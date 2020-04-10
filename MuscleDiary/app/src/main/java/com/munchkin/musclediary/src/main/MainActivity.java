package com.munchkin.musclediary.src.main;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.chart.ChartFragment;
import com.munchkin.musclediary.src.main.exercise.ExerciseFragment;
import com.munchkin.musclediary.src.main.food.FoodFragment;
import com.munchkin.musclediary.src.main.setting.SettingFragment;

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

    //텝레이아웃에 추가할 때 여기에 추가하는 함수
    public void setupViewPager(ViewPager viewPager) {
        mAdapter.addFragment(new FoodFragment(), "음식");
        mAdapter.addFragment(new ExerciseFragment(), "운동");
        mAdapter.addFragment(new ChartFragment(), "기록");
        mAdapter.addFragment(new SettingFragment(), "설정");
        viewPager.setAdapter(mAdapter);
    }
}
