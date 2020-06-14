package com.munchkin.musclediary.src.signin.models;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("id")
    String id;

    @SerializedName("password")
    String password;

    @SerializedName("rePassword")
    String rePassword;

    public SignUpRequest(String id, String password, String rePassword) {
        this.id = id;
        this.password = password;
        this.rePassword = rePassword;
    }
}
