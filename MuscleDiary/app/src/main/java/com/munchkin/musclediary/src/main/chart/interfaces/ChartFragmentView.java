package com.munchkin.musclediary.src.main.chart.interfaces;

import com.munchkin.musclediary.src.main.chart.models.WeightResult;

import java.util.ArrayList;

public interface ChartFragmentView {
    void getWeightSuccess(int code, String message, ArrayList<WeightResult> weightResults);
    void postWeightSuccess(int code, String message);
    void validateFailure(String message);
}