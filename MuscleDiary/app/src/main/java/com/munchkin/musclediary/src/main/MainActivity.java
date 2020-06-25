package com.munchkin.musclediary.src.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.chart.ChartFragment;
import com.munchkin.musclediary.src.main.exercise.ExerciseFragment;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;
import com.munchkin.musclediary.src.main.food.FoodFragment;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.munchkin.musclediary.src.main.setting.SettingFragment;

import java.io.Serializable;
import java.util.ArrayList;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class MainActivity extends BaseActivity {

    //뷰페이져 어뎁터
    private MainViewPagerAdapter mAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

    //임시 영양목표 칼로리, 탄단지 비율
    private int mKcal = 2024;
    private int mCarb = 50;
    private int mProtein = 30;
    private int mFat = 20;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //SharedPreferences에 영양목표, 탄단지 비율 저장
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt("kcal", mKcal);
        editor.putInt("carbohydrate", mCarb);
        editor.putInt("protein", mProtein);
        editor.putInt("fat", mFat);
        editor.apply();


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
                break;
            }

            case 2000: {
                ExerciseItem addExercise;
                addExercise = (ExerciseItem) data.getSerializableExtra("addExercise");

                //프레그먼트 캐스팅
                ExerciseFragment exerciseFragment = (ExerciseFragment) mAdapter.getItem(1);
                exerciseFragment.onCompleteExerciseSelect(addExercise,addExercise.getExercisePart());
                break;
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
