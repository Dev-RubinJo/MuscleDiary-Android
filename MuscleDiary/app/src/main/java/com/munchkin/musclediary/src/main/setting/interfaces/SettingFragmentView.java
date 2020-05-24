package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodResult;

import java.util.ArrayList;

public interface SettingFragmentView {
    void validateSuccess(int code, String message);
    void validateFailure(String message);
}