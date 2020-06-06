package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class UpdateProfileRequest {
    public UpdateProfileRequest(Double height, Double weight, int gender, Date birth) {
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.birth = birth;
    }
    @SerializedName("birth")
    private Date birth;

    @SerializedName("height")
    private Double height;

    @SerializedName("weight")
    private Double weight;

    @SerializedName("gender")
    private int gender;
}