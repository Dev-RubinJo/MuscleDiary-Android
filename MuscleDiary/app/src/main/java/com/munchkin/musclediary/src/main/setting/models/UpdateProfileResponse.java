package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {
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
