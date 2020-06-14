package com.munchkin.musclediary.src.main.setting.services;

import com.munchkin.musclediary.src.main.setting.interfaces.ProfileRetrofitInterface;
import com.munchkin.musclediary.src.main.setting.interfaces.SettingFragmentView;
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

    public void postUpdateProfile(Double height, Double weight, int gender, Date birth) {
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
}
