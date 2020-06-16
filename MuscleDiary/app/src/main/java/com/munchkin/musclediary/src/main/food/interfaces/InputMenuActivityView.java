package com.munchkin.musclediary.src.main.food.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.main.food.models.ReadFoodResult;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import java.util.ArrayList;

public interface InputMenuActivityView {
    void searchFoodListSuccess(int code, String message, ArrayList<FoodResult> result);
    void addFoodSuccess(int code, String message);
    void readMenuSuccess(int code, String message, ArrayList<ReadFoodResult> readFoodResults, int mealType);
    void deleteFoodSuccess(int code, String message);
    void getNutritionSuccess(int code, String message, GetNutritionResponse.NutritionResult result);
    void getGoalWeightFailure(String message);
    void validateFailure(String message);
}