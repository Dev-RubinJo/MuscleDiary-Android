package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodListResponse {
    @SerializedName("result")
    private ArrayList<FoodResult> result;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public ArrayList<FoodResult> getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
