package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.food.models.FoodListResponse;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileRequest;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UpdateProfileRetrofitInterface {
    @POST("/updateProfile")
    Call<UpdateProfileResponse> postUpdateProfile(@Body UpdateProfileRequest updateProfileRequest);
}