package com.munchkin.musclediary.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class ProfileResult {
    @SerializedName("height")
    private double height;

    @SerializedName("weight")
    private double weight;

    @SerializedName("gender")
    private int gender;

    @SerializedName("birth")
    private Date birth;

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getGender() {
        return gender;
    }

    public Date getBirth() {
        return birth;
    }
}
