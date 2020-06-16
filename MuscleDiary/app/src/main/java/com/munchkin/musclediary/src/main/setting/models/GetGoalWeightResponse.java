package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetGoalWeightResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public class Result {
        @SerializedName("goalWeight")
        private double goalWeight;

        @SerializedName("startWeight")
        private double startWeight;

        @SerializedName("currentWeight")
        private double currentWeight;

        public double getGoalWeight() {
            return goalWeight;
        }

        public double getStartWeight() {
            return startWeight;
        }

        public double getCurrentWeight() {
            return currentWeight;
        }
    }
}
