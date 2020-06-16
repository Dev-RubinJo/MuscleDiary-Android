package com.munchkin.musclediary.src.main.exercise.models;

import com.google.gson.annotations.SerializedName;

public class DeleteExerciseRequest {
    @SerializedName("exerciseNo")
    int exerciseNo;

    public DeleteExerciseRequest(int exerciseNo) {
        this.exerciseNo = exerciseNo;
    }
}
