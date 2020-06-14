package com.munchkin.musclediary.src.main.chart.models;

import com.google.gson.annotations.SerializedName;

public class WeightResult {
    @SerializedName("weight")
    private int weight;

    @SerializedName("recordDate")
    private String recordDate;

    public int getWeight() {
        return weight;
    }

    public String getDate() {
        return recordDate;
    }
}
