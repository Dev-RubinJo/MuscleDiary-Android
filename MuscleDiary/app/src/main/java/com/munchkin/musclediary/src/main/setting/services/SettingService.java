package com.munchkin.musclediary.src.main.setting.services;

import com.munchkin.musclediary.src.main.setting.interfaces.SettingFragmentView;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileRequest;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileResponse;
import com.munchkin.musclediary.src.main.setting.interfaces.UpdateProfileRetrofitInterface;

import java.sql.Date;
import java.sql.Timestamp;

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

                mSettingFragmentView.validateSuccess(updateProfileResponse.getCode(),
                        updateProfileResponse.getMessage());
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                mSettingFragmentView.validateFailure("fail");
            }
        });
    }
}
