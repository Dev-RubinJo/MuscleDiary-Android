package com.munchkin.musclediary.src.signin.services;

import android.util.Log;

import com.munchkin.musclediary.src.signin.interfaces.SignInActivityView;
import com.munchkin.musclediary.src.signin.interfaces.SignInRetrofitInterface;
import com.munchkin.musclediary.src.signin.models.SignInRequest;
import com.munchkin.musclediary.src.signin.models.SignInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class SignInService {
    private final SignInActivityView mSignInActivityView;

    public SignInService(final SignInActivityView signInActivityView) {
        this.mSignInActivityView = signInActivityView;
    }

    public void postSignIn(String id,String password) {
        final SignInRetrofitInterface signInRetrofitInterface = getRetrofit().create(SignInRetrofitInterface.class);

        signInRetrofitInterface.postSignIn(new SignInRequest(id,password)).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                final SignInResponse signInResponse = response.body();
                if (signInResponse == null) {
                    mSignInActivityView.validateFailure("null");
                    return;
                }

                mSignInActivityView.validateSuccess(signInResponse.getCode(),
                        signInResponse.getMessage(), signInResponse.getJwt());
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                mSignInActivityView.validateFailure("fail");
            }
        });
    }
}
