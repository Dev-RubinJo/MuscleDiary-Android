package com.munchkin.musclediary.src.main.exercise.models;

import com.google.gson.annotations.SerializedName;

public class ExerciseResult {
    @SerializedName("exerciseNo")
    int exerciseNo;
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
    Double weight;

    public int getExerciseNo() {
        return exerciseNo;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getExercisePart() {
        return exercisePart;
    }

    public Integer getSetNo() {
        return setNo;
    }

    public Integer getRepeatNo() {
        return repeatNo;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public Double getWeight() {
        return weight;
    }
}
