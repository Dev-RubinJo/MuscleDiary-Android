package com.munchkin.musclediary.src.main.food.interfaces;

import com.munchkin.musclediary.src.main.food.models.DeleteFoodRequest;
import com.munchkin.musclediary.src.main.food.models.DeleteFoodResponse;
import com.munchkin.musclediary.src.main.food.models.FoodAddRequest;
import com.munchkin.musclediary.src.main.food.models.FoodAddResponse;
import com.munchkin.musclediary.src.main.food.models.FoodListResponse;
import com.munchkin.musclediary.src.main.food.models.ReadFoodResponse;
import com.munchkin.musclediary.src.signin.models.SignInRequest;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodListRetrofitInterface {
    @GET("/foodList")
    Call<FoodListResponse> getFoodList(@Query("keyword")String keyword);

    @POST("/user/meal")
    Call<FoodAddResponse> postAddFood(@Body FoodAddRequest foodAddRequest);

    @GET("/user/meal")
    Call<ReadFoodResponse> getReadFood(@Query("mealType")int mealType, @Query("recordDate")String recoedDate);

    @HTTP(method = "DELETE", path = "/user/meal", hasBody = true)
    Call<DeleteFoodResponse> deleteFood(@Body DeleteFoodRequest deleteFoodRequest);
}