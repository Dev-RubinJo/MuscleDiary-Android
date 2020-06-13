package com.munchkin.musclediary.src.main.chart.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetWeightResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<WeightResult> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<WeightResult> getResult(){
        return result;
    }


}
