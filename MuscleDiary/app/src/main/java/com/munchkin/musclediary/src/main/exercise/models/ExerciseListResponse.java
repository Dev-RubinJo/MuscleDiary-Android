package com.munchkin.musclediary.src.main.exercise.models;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class ExerciseListResponse {
    @SerializedName("result")
    private ArrayList<ExerciseResult> result;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public ArrayList<ExerciseResult> getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
