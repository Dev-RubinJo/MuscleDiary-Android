package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReadFoodResponse {
    @SerializedName("result")
    ArrayList<ReadFoodResult> result;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public ArrayList<ReadFoodResult> getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
