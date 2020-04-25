package com.munchkin.musclediary.src.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.setting.dialog.AgeActivity;
import com.munchkin.musclediary.src.main.setting.dialog.GenderActivity;
import com.munchkin.musclediary.src.main.setting.dialog.HeightActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    //startActivityForResult requestCode
    private final int ADD_MEAL = 0;
    private final int CHANGE_GENDER = 1;
    private final int CHANGE_AGE = 2;
    private final int CHANGE_HEIGHT = 3;

    //프로필 변경 버튼
    private Button mBtGender;
    private Button mBtAge;
    private Button mBtHeight;

    private ArrayList<SettingItem> mItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 세팅 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {

        //성별 변경 버튼
        mBtGender = v.findViewById(R.id.bt_gender_setting);
        mBtGender.setOnClickListener(this);

        //생년월일 변경 버튼
        mBtAge = v.findViewById(R.id.bt_age_setting);
        mBtAge.setOnClickListener(this);

        //키 변경 버튼
        mBtHeight = v.findViewById(R.id.bt_height_setting);
        mBtHeight.setOnClickListener(this);

        //끼니 추가 버튼
        Button btAdd = v.findViewById(R.id.bt_add_setting);
        btAdd.setOnClickListener(this);

        //더미데이터 넣는 함수 실행
        addRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = v.findViewById(R.id.recycler_setting);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        SettingAdapter adapter = new SettingAdapter(getContext(), mItems);
        chartRecyclerView.setAdapter(adapter);
    }

    //리사이클러뷰 리스트 아이템 채우는 함수
    private void addRecyclerList(){
        //더미데이터들
        String[] mealList = {"아침", "점심", "저녁"};
        mItems = new ArrayList<>();

        for (String meal : mealList) {
            SettingItem item = new SettingItem();
            item.setMeal(meal);
            mItems.add(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }
        switch(requestCode){
            case ADD_MEAL:
                //추가했을 때 리스트에 추가하는 코드 필요
                break;

            case CHANGE_GENDER:
                //성별 변경했을 때 코드
                if(data.getStringExtra("gender") != null){
                    mBtGender.setText(data.getStringExtra("gender"));
                }
                break;

            case CHANGE_AGE:
                //생년월일 변경했을 때 코드
                mBtAge.setText(data.getStringExtra("age"));
                break;

            case CHANGE_HEIGHT:
                //키 변경했을 때 코드
                mBtHeight.setText(data.getStringExtra("height"));
                break;

            default:
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //끼니 추가 버튼 클릭이벤트
            case R.id.bt_add_setting:
                Intent inputIntent = new Intent(getActivity(), InputSettingActivity.class);
                startActivityForResult(inputIntent, ADD_MEAL);
                break;

            //성별 변경 버튼 클릭이벤트
            case R.id.bt_gender_setting:
                Intent genderIntent = new Intent(getActivity(), GenderActivity.class);
                startActivityForResult(genderIntent, CHANGE_GENDER);
                break;

            //생일 변경 버튼 클릭이벤트
            case R.id.bt_age_setting:
                Intent ageIntent = new Intent(getActivity(), AgeActivity.class);
                ageIntent.putExtra("year", 1996);
                ageIntent.putExtra("month", 6);
                ageIntent.putExtra("date", 3);
                startActivityForResult(ageIntent, CHANGE_AGE);
                break;

            //키 변경 버튼 클릭이벤트
            case R.id.bt_height_setting:
                Intent heightIntent = new Intent(getActivity(), HeightActivity.class);
                mBtHeight.getText();
                int integer = Integer.parseInt(mBtHeight.getText().toString().substring(0,3));
                int dot = Integer.parseInt(mBtHeight.getText().toString().substring(4,5));
                heightIntent.putExtra("integer", integer);
                heightIntent.putExtra("dot", dot);
                startActivityForResult(heightIntent, CHANGE_HEIGHT);
                break;
            default:
                break;
        }
    }
}
