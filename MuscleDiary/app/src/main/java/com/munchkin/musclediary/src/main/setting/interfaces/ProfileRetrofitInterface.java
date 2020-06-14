package com.munchkin.musclediary.src.main.setting.interfaces;

import com.munchkin.musclediary.src.main.setting.models.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProfileRetrofitInterface {
    @GET("/profile")
    Call<ProfileResponse> getProfile();
}
