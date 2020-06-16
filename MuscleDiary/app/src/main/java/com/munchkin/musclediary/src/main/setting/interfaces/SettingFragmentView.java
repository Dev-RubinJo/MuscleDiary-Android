package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.GetGoalWeightResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResult;

import java.util.ArrayList;

public interface SettingFragmentView {
    void updateProfileSuccess(int code, String message);
    void profileSuccess(int code, String message, ArrayList<ProfileResult> profileResult);
    void postNutritionSuccess(int code, String message);
    void getNutritionSuccess(int code, String message, GetNutritionResponse.NutritionResult result);
    void getGoalWeightSuccess(int code, String message, GetGoalWeightResponse.Result result);
    void postGoalWeightSuccess(int code, String message);
    void getGoalWeightFailure(String message);
    void validateFailure(String message);
}