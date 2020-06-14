package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class PostNutritionRequest {
    public PostNutritionRequest(int carboRate, int proteinRate, int fatRate, int goalCalorie) {
        this.carboRate = carboRate;
        this.proteinRate = proteinRate;
        this.fatRate = fatRate;
        this.goalCalorie = goalCalorie;
    }
    @SerializedName("carboRate")
    private int carboRate;

    @SerializedName("proteinRate")
    private int proteinRate;

    @SerializedName("fatRate")
    private int fatRate;

    @SerializedName("goalCalorie")
    private int goalCalorie;
}