package com.munchkin.musclediary.src.signin.models;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
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
