package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.PostNutritionRequest;
import com.munchkin.musclediary.src.main.setting.models.PostNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GoalNutritionRetrofitInterface {
    @POST("/user/goalNutrition")
    Call<PostNutritionResponse> postNutrition(@Body PostNutritionRequest postNutritionRequest);
    @GET("/user/goalNutrition")
    Call<GetNutritionResponse> getNutrition();
}
