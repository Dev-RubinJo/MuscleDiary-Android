package com.munchkin.musclediary.src.signin.services;

import com.munchkin.musclediary.src.signin.interfaces.SignInActivityView;
import com.munchkin.musclediary.src.signin.interfaces.SignInRetrofitInterface;
import com.munchkin.musclediary.src.signin.models.SignInRequest;
import com.munchkin.musclediary.src.signin.models.SignInResponse;
import com.munchkin.musclediary.src.signin.models.SignUpRequest;
import com.munchkin.musclediary.src.signin.models.SignUpResponse;

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

                mSignInActivityView.SignInSuccess(signInResponse.getCode(),
                        signInResponse.getMessage(), signInResponse.getJwt());
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                mSignInActivityView.validateFailure("fail");
            }
        });
    }

    public void postSignUp(String id, String password, String rePassword) {
        final SignInRetrofitInterface signInRetrofitInterface = getRetrofit().create(SignInRetrofitInterface.class);

        signInRetrofitInterface.postSignUp(new SignUpRequest(id,password,rePassword)).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                final SignUpResponse signUpResponse = response.body();
                if (signUpResponse == null); {
                    mSignInActivityView.validateFailure("null");
                }

                mSignInActivityView.SignUpSuccess(signUpResponse.getCode(),
                        signUpResponse.getMessage());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                mSignInActivityView.validateFailure("null");
            }
        });
    }
}
