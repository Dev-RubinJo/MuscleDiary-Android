package com.munchkin.musclediary.src.signin.interfaces;

import com.munchkin.musclediary.src.signin.models.SignInRequest;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInRetrofitInterface {
    @POST("/login")
    Call<SignInResponse> postSignIn(@Body SignInRequest signInRequest);
}