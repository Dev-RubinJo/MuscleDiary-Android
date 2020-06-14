package com.munchkin.musclediary.src.main.food.models;

import com.google.gson.annotations.SerializedName;

public class FoodAddResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
