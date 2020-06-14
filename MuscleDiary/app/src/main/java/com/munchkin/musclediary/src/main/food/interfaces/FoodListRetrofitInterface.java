package com.munchkin.musclediary.src.main.food.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodAddRequest;
import com.munchkin.musclediary.src.main.food.models.FoodAddResponse;
import com.munchkin.musclediary.src.main.food.models.FoodListResponse;
import com.munchkin.musclediary.src.signin.models.SignInRequest;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodListRetrofitInterface {
    @GET("/foodList")
    Call<FoodListResponse> getFoodList(@Query("keyword")String keyword);

    @POST("/user/meal")
    Call<FoodAddResponse> postAddFood(@Body FoodAddRequest foodAddRequest);
}