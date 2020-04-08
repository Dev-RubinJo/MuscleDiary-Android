package com.munchkin.musclediary.src.main;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;

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
        //mAdapter.addFragment(new ExampleFragment(), "예시");
        viewPager.setAdapter(mAdapter);
    }
}
