package com.munchkin.musclediary.src.main.setting.services;

import android.util.Log;

import com.munchkin.musclediary.src.main.setting.interfaces.GoalNutritionRetrofitInterface;
import com.munchkin.musclediary.src.main.setting.interfaces.ProfileRetrofitInterface;
import com.munchkin.musclediary.src.main.setting.interfaces.SettingFragmentView;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.PostNutritionRequest;
import com.munchkin.musclediary.src.main.setting.models.PostNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResponse;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileRequest;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileResponse;
import com.munchkin.musclediary.src.main.setting.interfaces.UpdateProfileRetrofitInterface;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class SettingService {
    private final SettingFragmentView mSettingFragmentView;

    public SettingService(final SettingFragmentView settingFragmentView) {
        this.mSettingFragmentView = settingFragmentView;
    }

    public void postUpdateProfile(Double height, Double weight, int gender, String birth) {
        final UpdateProfileRetrofitInterface updateProfileRetrofitInterface = getRetrofit().create(UpdateProfileRetrofitInterface.class);

        updateProfileRetrofitInterface.postUpdateProfile(new UpdateProfileRequest(height, weight, gender, birth)).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                final UpdateProfileResponse updateProfileResponse = response.body();
                if (updateProfileResponse == null) {
                    mSettingFragmentView.validateFailure("null");
                    return;
                }

                mSettingFragmentView.updateProfileSuccess(updateProfileResponse.getCode(),
                        updateProfileResponse.getMessage());
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                mSettingFragmentView.validateFailure("fail");
            }
        });
    }

    public void getProfile(){
        final ProfileRetrofitInterface profileRetrofitInterface = getRetrofit().create(ProfileRetrofitInterface.class);

        profileRetrofitInterface.getProfile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                final ProfileResponse profileResponse = response.body();
                if (profileResponse == null) {
                    mSettingFragmentView.validateFailure("null");
                    return;
                }

                mSettingFragmentView.profileSuccess(profileResponse.getCode(),
                        profileResponse.getMessage(),
                        profileResponse.getResult());
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mSettingFragmentView.validateFailure("fail");
            }
        });
    }

    public void getNutrition(){
        final GoalNutritionRetrofitInterface goalNutritionRetrofitInterface = getRetrofit().create(GoalNutritionRetrofitInterface.class);
        goalNutritionRetrofitInterface.getNutrition().enqueue(new Callback<GetNutritionResponse>() {
            @Override
            public void onResponse(Call<GetNutritionResponse> call, Response<GetNutritionResponse> response) {
                final GetNutritionResponse getNutritionResponse = response.body();
                if (getNutritionResponse == null) {
                    mSettingFragmentView.validateFailure("null");
                    return;
                }
                Log.d("inTest", getNutritionResponse.getMessage());
                mSettingFragmentView.getNutritionSuccess(getNutritionResponse.getCode(),
                        getNutritionResponse.getMessage(),
                        getNutritionResponse.getResult().get(0));
            }
            @Override
            public void onFailure(Call<GetNutritionResponse> call, Throwable t) {
                mSettingFragmentView.validateFailure("fail");
            }
        });
    }

    public void postNutrition(int carboRate, int proteinRate, int fatRate, int goalCalorie) {
        final GoalNutritionRetrofitInterface goalNutritionRetrofitInterface = getRetrofit().create(GoalNutritionRetrofitInterface.class);

        goalNutritionRetrofitInterface.postNutrition(new PostNutritionRequest(carboRate, proteinRate, fatRate, goalCalorie)).enqueue(new Callback<PostNutritionResponse>() {
            @Override
            public void onResponse(Call<PostNutritionResponse> call, Response<PostNutritionResponse> response) {
                final PostNutritionResponse postNutritionResponse = response.body();
                if (postNutritionResponse == null) {
                    mSettingFragmentView.validateFailure("null");
                    return;
                }

                mSettingFragmentView.postNutritionSuccess(postNutritionResponse.getCode(),
                        postNutritionResponse.getMessage());
            }

            @Override
            public void onFailure(Call<PostNutritionResponse> call, Throwable t) {
                mSettingFragmentView.validateFailure("fail");
            }
        });
    }
}
