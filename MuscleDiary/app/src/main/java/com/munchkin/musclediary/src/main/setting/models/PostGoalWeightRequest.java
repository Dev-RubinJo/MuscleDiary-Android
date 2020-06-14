package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

public class PostGoalWeightRequest {
    public PostGoalWeightRequest(Double goalWeight) {
        this.goalWeight = goalWeight;
    }
    @SerializedName("goalWeight")
    private Double goalWeight;
}