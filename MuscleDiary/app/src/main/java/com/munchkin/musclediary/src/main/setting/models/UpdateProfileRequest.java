package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    public UpdateProfileRequest(String height, String weight, String age, String gender) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;

    @SerializedName("age")
    private String age;

    @SerializedName("gender")
    private String gender;
}