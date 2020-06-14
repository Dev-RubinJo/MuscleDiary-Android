package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetNutritionResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<NutritionResult> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<NutritionResult> getResult() {
        return result;
    }

    public class NutritionResult {
        @SerializedName("carboRate")
        private int carboRate;

        @SerializedName("proteinRate")
        private int proteinRate;

        @SerializedName("fatRate")
        private int fatRate;

        @SerializedName("goalCalorie")
        private int goalCalorie;

        public int getCarboRate() {
            return carboRate;
        }

        public int getProteinRate() {
            return proteinRate;
        }

        public int getFatRate() {
            return fatRate;
        }

        public int getGoalCalorie() {
            return goalCalorie;
        }
    }
}
