package com.munchkin.musclediary.src.signin.models;

import com.google.gson.annotations.SerializedName;

public class SignInRequest {

    public SignInRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("password")
    private String password;
}