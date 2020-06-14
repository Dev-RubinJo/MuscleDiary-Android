package com.munchkin.musclediary.src.main.chart.interfaces;

import com.munchkin.musclediary.src.main.chart.models.PostWeightRequest;
import com.munchkin.musclediary.src.main.chart.models.PostWeightResponse;
import com.munchkin.musclediary.src.main.chart.models.GetWeightResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserWeightRetrofitInterface {
    @POST("/user/weight")
    Call<PostWeightResponse> postWeight(@Body PostWeightRequest postWeightRequest);

    @GET("/user/weekly/weight")
    Call<GetWeightResponse> getWeekWeight(@Query("weekDate")String weekDate);

    @GET("/user/monthly/weight")
    Call<GetWeightResponse> getMonthWeight(@Query("monthDate")String monthDate);

    @GET("/user/yearly/weight")
    Call<GetWeightResponse> getYearWeight(@Query("yearDate")String yearDate);
}