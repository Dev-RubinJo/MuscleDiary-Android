package com.munchkin.musclediary.src.main.exercise.models;

import com.google.gson.annotations.SerializedName;

public class DeleteExerciseResponse {
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
