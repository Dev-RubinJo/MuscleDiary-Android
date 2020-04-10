package com.munchkin.musclediary.src.main.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;

public class ExerciseFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 운동 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {

    }
}
