package com.munchkin.musclediary.src.main.chart.services;

import com.munchkin.musclediary.src.main.chart.interfaces.ChartFragmentView;
import com.munchkin.musclediary.src.main.chart.interfaces.UserWeightRetrofitInterface;
import com.munchkin.musclediary.src.main.chart.models.GetWeightResponse;
import com.munchkin.musclediary.src.main.chart.models.PostWeightRequest;
import com.munchkin.musclediary.src.main.chart.models.PostWeightResponse;
import com.munchkin.musclediary.src.main.setting.models.UpdateProfileRequest;
import com.munchkin.musclediary.src.main.chart.models.PostWeightResponse;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class ChartService {
    private final ChartFragmentView mChartFragmentView;

    public ChartService(final ChartFragmentView chartFragmentView) {
        this.mChartFragmentView = chartFragmentView;
    }

    public void postWeight(float weight, String recordDate) {
        final UserWeightRetrofitInterface userWeightRetorfitInterface = getRetrofit().create(UserWeightRetrofitInterface.class);

        userWeightRetorfitInterface.postWeight(new PostWeightRequest(weight, recordDate)).enqueue(new Callback<PostWeightResponse>() {
            @Override
            public void onResponse(Call<PostWeightResponse> call, Response<PostWeightResponse> response) {
                final PostWeightResponse postWeightResponse = response.body();
                if (postWeightResponse == null) {
                    mChartFragmentView.validateFailure("null");
                    return;
                }

                mChartFragmentView.postWeightSuccess(postWeightResponse.getCode(),
                        postWeightResponse.getMessage());
            }

            @Override
            public void onFailure(Call<PostWeightResponse> call, Throwable t) {
                mChartFragmentView.validateFailure("fail");
            }
        });
    }

    public void getWeekWeight(int date){
        final UserWeightRetrofitInterface userWeightRetorfitInterface = getRetrofit().create(UserWeightRetrofitInterface.class);

        userWeightRetorfitInterface.getWeekWeight(date).enqueue(new Callback<GetWeightResponse>() {
            @Override
            public void onResponse(Call<GetWeightResponse> call, Response<GetWeightResponse> response) {
                final GetWeightResponse getWeightResponse = response.body();
                if (getWeightResponse == null) {
                    mChartFragmentView.validateFailure("null");
                    return;
                }

                mChartFragmentView.getWeightSuccess(getWeightResponse.getCode(),
                        getWeightResponse.getMessage(), getWeightResponse.getResult());
            }
            @Override
            public void onFailure(Call<GetWeightResponse> call, Throwable t) {
                mChartFragmentView.validateFailure("fail");
            }
        });
    }

    public void getMonthWeight(int date){
        final UserWeightRetrofitInterface userWeightRetorfitInterface = getRetrofit().create(UserWeightRetrofitInterface.class);

        userWeightRetorfitInterface.getMonthWeight(date).enqueue(new Callback<GetWeightResponse>() {
            @Override
            public void onResponse(Call<GetWeightResponse> call, Response<GetWeightResponse> response) {
                final GetWeightResponse getWeightResponse = response.body();
                if (getWeightResponse == null) {
                    mChartFragmentView.validateFailure("null");
                    return;
                }

                mChartFragmentView.getWeightSuccess(getWeightResponse.getCode(),
                        getWeightResponse.getMessage(), getWeightResponse.getResult());
            }
            @Override
            public void onFailure(Call<GetWeightResponse> call, Throwable t) {
                mChartFragmentView.validateFailure("fail");
            }
        });
    }

    public void getYearWeight(int date){
        final UserWeightRetrofitInterface userWeightRetorfitInterface = getRetrofit().create(UserWeightRetrofitInterface.class);

        userWeightRetorfitInterface.getYearWeight(date).enqueue(new Callback<GetWeightResponse>() {
            @Override
            public void onResponse(Call<GetWeightResponse> call, Response<GetWeightResponse> response) {
                final GetWeightResponse getWeightResponse = response.body();
                if (getWeightResponse == null) {
                    mChartFragmentView.validateFailure("null");
                    return;
                }

                mChartFragmentView.getWeightSuccess(getWeightResponse.getCode(),
                        getWeightResponse.getMessage(), getWeightResponse.getResult());
            }
            @Override
            public void onFailure(Call<GetWeightResponse> call, Throwable t) {
                mChartFragmentView.validateFailure("fail");
            }
        });
    }
}
