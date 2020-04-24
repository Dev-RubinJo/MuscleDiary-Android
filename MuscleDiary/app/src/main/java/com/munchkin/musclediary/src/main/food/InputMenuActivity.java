package com.munchkin.musclediary.src.main.food;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.food.adapter.MenuResultAdapter;
import com.munchkin.musclediary.src.main.food.models.MenuItem;

import java.util.ArrayList;

public class InputMenuActivity extends BaseActivity {

    String mMealTitle;
    TextView mTvMealTitle;
    RecyclerView mMenuResultRecyclerView;

    private ArrayList<MenuItem> mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_menu);

        //인텐트 받아오기 (끼니 제목)
        Intent getIntent = getIntent();
        mMealTitle = getIntent.getStringExtra("mealTitle");

        //레이아웃 컴포넌트 설정
        mTvMealTitle = findViewById(R.id.input_menu_tv_meal_title);
        mMenuResultRecyclerView = findViewById(R.id.input_menu_rv_menu_result);

        //제목을 선택한 끼니로 바꿔줌
        mTvMealTitle.setText(mMealTitle);

        //더미데이터 생성
        addMealList();

        //리사이클러뷰 레이아웃 매니저 적용
        mMenuResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MenuResultAdapter menuResultAdapter = new MenuResultAdapter(getApplicationContext(),mMenuItems);
        mMenuResultRecyclerView.setAdapter(menuResultAdapter);
    }

    private void addMealList(){

        String menutitleList[] = {"계란밥","된장국","스파케티","사과주스"};
        double menuKcalList[] = {50,12,102,124.2,24,13,25,354.3,113,13,112,113.4,23,24,25,231.4};

        String menutitleList2[] = {"스테이크","연어","단백질파우더"};
        double menuKcalList2[] = {50,12,102,432.2,24,13,25,434.3,113,13,112,145.2};

        mMenuItems = new ArrayList<>();

        //최근 입력 더미데이터
        for(int j =0; j<3; j++){
            MenuItem menuItem = new MenuItem(menutitleList2[j],menuKcalList2[4*j],menuKcalList2[(4*j)+1],
                    menuKcalList2[(4*j)+2],menuKcalList2[(4*j)+3]);
            mMenuItems.add(menuItem);
        }

        //최근 입력 더미데이터 2 >> fragment food 내용 재사용
        for(int j =0; j<4; j++){
            MenuItem menuItem = new MenuItem(menutitleList[j],menuKcalList[4*j],menuKcalList[(4*j)+1],
                    menuKcalList[(4*j)+2],menuKcalList[(4*j)+3]);
            mMenuItems.add(menuItem);
        }
    }
}
