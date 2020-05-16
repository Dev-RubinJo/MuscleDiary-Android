package com.munchkin.musclediary.src.signin.models;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("result")
    private Jwt jwt;

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public Jwt getJwt() {
        return jwt;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class Jwt{
        @SerializedName("jwt")
        private String jwt;

        public String getJwt() {
            return jwt;
        }
    }
}