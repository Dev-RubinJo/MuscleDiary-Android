package com.munchkin.musclediary.src.main.chart.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class PostWeightRequest {
    public PostWeightRequest(float weight, String recordDate) {
        this.weight = weight;
        this.recordDate = recordDate;
    }
    @SerializedName("weight")
    private float weight;

    @SerializedName("recordDate")
    private String recordDate;

}