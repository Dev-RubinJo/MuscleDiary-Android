package com.munchkin.musclediary.src.main.food.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import java.util.ArrayList;

public interface InputMenuActivityView {
    void searchFoodListSuccess(int code, String message, ArrayList<FoodResult> result);
    void addFoodSuccess(int code, String message);
    void validateFailure(String message);
}