package com.munchkin.musclediary.src.main.exercise.models;

import com.google.gson.annotations.SerializedName;

public class AddExerciseRequest {
    @SerializedName("exerciseName")
    String exerciseName;
    @SerializedName("exercisePart")
    int exercisePart;
    @SerializedName("setNo")
    Integer setNo;
    @SerializedName("repeatNo")
    Integer repeatNo;
    @SerializedName("min")
    Integer min;
    @SerializedName("intensity")
    Integer intensity;
    @SerializedName("weight")
    Integer weight;
    @SerializedName("recordDate")
    String recordDate;

    public AddExerciseRequest(String exerciseName, int exercisePart, Integer setNo,
                              Integer repeatNo, Integer min,
                              Integer intensity, Integer weight,
                              String recordDate) {
        this.exerciseName = exerciseName;
        this.exercisePart = exercisePart;
        this.setNo = setNo;
        this.repeatNo = repeatNo;
        this.min = min;
        this.intensity = intensity;
        this.weight = weight;
        this.recordDate = recordDate;
    }
}
