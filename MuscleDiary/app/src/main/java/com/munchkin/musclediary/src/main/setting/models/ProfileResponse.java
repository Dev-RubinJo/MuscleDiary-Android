package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileResponse {
    @SerializedName("result")
    private ArrayList<ProfileResult> result;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public ArrayList<ProfileResult> getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
