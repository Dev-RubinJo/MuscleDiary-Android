package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.setting.models.GetGoalWeightResponse;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.PostGoalWeightRequest;
import com.munchkin.musclediary.src.main.setting.models.PostGoalWeightResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GoalWeightRetrofitInterface {
    @POST("/user/goalWeight")
    Call<PostGoalWeightResponse> postGoalWeight(@Body PostGoalWeightRequest postGoalWeightRequest);
    @GET("/user/goalWeight")
    Call<GetGoalWeightResponse> getGoalWeight();
}
