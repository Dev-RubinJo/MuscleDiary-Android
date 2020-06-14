package com.munchkin.musclediary.src.main.food.services;

import com.munchkin.musclediary.src.main.food.interfaces.FoodListRetrofitInterface;
import com.munchkin.musclediary.src.main.food.interfaces.InputMenuActivityView;
import com.munchkin.musclediary.src.main.food.models.FoodAddRequest;
import com.munchkin.musclediary.src.main.food.models.FoodAddResponse;
import com.munchkin.musclediary.src.main.food.models.FoodListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class InputMenuService {
    private final InputMenuActivityView mInputMenuActivityView;

    public InputMenuService(final InputMenuActivityView inputMenuActivityView) {
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
            }

            @Override
            public void onFailure(Call<FoodAddResponse> call, Throwable t) {
                mInputMenuActivityView.validateFailure("null");
            }
        });
    }
}
