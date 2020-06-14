package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.main.setting.models.ProfileResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResult;

import java.util.ArrayList;

public interface SettingFragmentView {
    void updateProfileSuccess(int code, String message);
    void profileSuccess(int code, String message, ArrayList<ProfileResult> profileResult);
    void validateFailure(String message);
}