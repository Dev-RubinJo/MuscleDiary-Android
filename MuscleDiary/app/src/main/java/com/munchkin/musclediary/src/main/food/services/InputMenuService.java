package com.munchkin.musclediary.src.main.food.services;

import android.util.Log;

import com.munchkin.musclediary.src.main.food.interfaces.FoodListRetrofitInterface;
import com.munchkin.musclediary.src.main.food.interfaces.InputMenuActivityView;
import com.munchkin.musclediary.src.main.food.models.DeleteFoodRequest;
import com.munchkin.musclediary.src.main.food.models.DeleteFoodResponse;
import com.munchkin.musclediary.src.main.food.models.FoodAddRequest;
import com.munchkin.musclediary.src.main.food.models.FoodAddResponse;
import com.munchkin.musclediary.src.main.food.models.FoodListResponse;
import com.munchkin.musclediary.src.main.food.models.ReadFoodResponse;
import com.munchkin.musclediary.src.main.setting.interfaces.GoalNutritionRetrofitInterface;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class InputMenuService {
    private final InputMenuActivityView mInputMenuActivityView;

    public InputMenuService(InputMenuActivityView inputMenuActivityView) {
        this.mInputMenuActivityView = inputMenuActivityView;
    }

    public void getFoodList(String keyword) {
        final FoodListRetrofitInterface foodListRetrofitInterface = getRetrofit().create(FoodListRetrofitInterface.class);

        foodListRetrofitInterface.getFoodList(keyword).enqueue(new Callback<FoodListResponse>() {
            @Override
            public void onResponse(Call<FoodListResponse> call, Response<FoodListResponse> response) {
                final FoodListResponse foodListResponse = response.body();
                if (foodListResponse == null) {
                    mInputMenuActivityView.validateFailure("null");
                    return;
                }

                mInputMenuActivityView.searchFoodListSuccess(foodListResponse.getCode(),
                        foodListResponse.getMessage(), foodListResponse.getResult());
            }

            @Override
            public void onFailure(Call<FoodListResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("fail");
            }
        });
    }

    public void postAddFood(FoodAddRequest foodAddRequest){
        final FoodListRetrofitInterface foodListRetrofitInterface = getRetrofit().create(FoodListRetrofitInterface.class);

        foodListRetrofitInterface.postAddFood(foodAddRequest).enqueue(new Callback<FoodAddResponse>() {
            @Override
            public void onResponse(Call<FoodAddResponse> call, Response<FoodAddResponse> response) {
                final FoodAddResponse foodAddResponse= response.body();
                if(foodAddResponse == null){
                    mInputMenuActivityView.validateFailure("null");
                    return;
                }

                mInputMenuActivityView.addFoodSuccess(foodAddResponse.getCode(),
                        foodAddResponse.getMessage());
                Log.d("Debug", "음식입력 완료");
            }

            @Override
            public void onFailure(Call<FoodAddResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("null");
            }
        });
    }

    public void readFoodList(final int mealType, String recordDate){
        final FoodListRetrofitInterface foodListRetrofitInterface = getRetrofit().create(FoodListRetrofitInterface.class);

        foodListRetrofitInterface.getReadFood(mealType,recordDate).enqueue(new Callback<ReadFoodResponse>() {
            @Override
            public void onResponse(Call<ReadFoodResponse> call, Response<ReadFoodResponse> response) {
                final ReadFoodResponse readFoodResponse = response.body();
                if(readFoodResponse == null){
                    mInputMenuActivityView.validateFailure("null");
                    return;
                }

                mInputMenuActivityView.readMenuSuccess(readFoodResponse.getCode(),
                        readFoodResponse.getMessage(), readFoodResponse.getResult(), mealType);
            }

            @Override
            public void onFailure(Call<ReadFoodResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("null");
            }
        });
    }

    public void deleteFood(final DeleteFoodRequest deleteFoodRequest){
        final FoodListRetrofitInterface foodListRetrofitInterface = getRetrofit().create(FoodListRetrofitInterface.class);

        foodListRetrofitInterface.deleteFood(deleteFoodRequest).enqueue(new Callback<DeleteFoodResponse>() {
            @Override
            public void onResponse(Call<DeleteFoodResponse> call, Response<DeleteFoodResponse> response) {
                final DeleteFoodResponse deleteFoodResponse = response.body();
                if(deleteFoodResponse == null){
                    mInputMenuActivityView.validateFailure("null");
                    return;
                }

                mInputMenuActivityView.deleteFoodSuccess(deleteFoodResponse.getCode(),
                        deleteFoodResponse.getMessage());

            }

            @Override
            public void onFailure(Call<DeleteFoodResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("null");
            }
        });
    }

    public void getNutrition(){
        final GoalNutritionRetrofitInterface goalNutritionRetrofitInterface = getRetrofit().create(GoalNutritionRetrofitInterface.class);
        goalNutritionRetrofitInterface.getNutrition().enqueue(new Callback<GetNutritionResponse>() {
            @Override
            public void onResponse(Call<GetNutritionResponse> call, Response<GetNutritionResponse> response) {
                final GetNutritionResponse getNutritionResponse = response.body();
                if (getNutritionResponse == null) {
                    mInputMenuActivityView.validateFailure("null");
                    return;
                }
                if(getNutritionResponse.getCode() == 102){
                    mInputMenuActivityView.getNutritionSuccess(getNutritionResponse.getCode(),
                            getNutritionResponse.getMessage(),
                            getNutritionResponse.getResult().get(0));
                } else {
                    mInputMenuActivityView.getGoalWeightFailure(getNutritionResponse.getMessage());
                }

            }
            @Override
            public void onFailure(Call<GetNutritionResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("fail");
            }
        });
    }
}
