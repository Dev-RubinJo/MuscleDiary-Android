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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends BaseFragment {

    //startActivityForResult requestCode
    private final int ADD_MEAL = 0;

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

        //클릭 리스너 생성
        View.OnClickListener listener = onClickListener();

        //끼니 추가 버튼
        Button btAdd = v.findViewById(R.id.bt_add_setting);
        btAdd.setOnClickListener(listener);

        //더미데이터 넣는 함수 실행
        addRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = v.findViewById(R.id.recycler_setting);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        SettingAdapter adapter = new SettingAdapter(getContext(), mItems);
        chartRecyclerView.setAdapter(adapter);
    }

    //클릭 리스너 모음
    private View.OnClickListener onClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    //끼니 추가 버튼 클릭이벤트
                    case R.id.bt_add_setting:
                        Intent intent = new Intent(getActivity(), InputSettingActivity.class);
                        startActivityForResult(intent, ADD_MEAL);
                        break;
                    default:
                        break;
                }
            }
        };
        return listener;
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
            default:
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
