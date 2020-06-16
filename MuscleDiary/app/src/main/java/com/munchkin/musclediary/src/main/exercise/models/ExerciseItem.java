package com.munchkin.musclediary.src.main.exercise.models;

import android.widget.DatePicker;

import java.io.Serializable;

public class ExerciseItem implements Serializable {

    private int exerciseNo;
    private String exerciseName;
    private String exercisePart;
    private Integer set;
    private Integer repeat;
    private Integer min;
    private Integer intensity;
    private Double weight;

    public ExerciseItem(String exerciseName, String exercisePart) {
        this.exerciseName = exerciseName;
        this.exercisePart = exercisePart;
    }

    public int getExerciseNo() {
        return exerciseNo;
    }

    public void setExerciseNo(int exerciseNo) {
        this.exerciseNo = exerciseNo;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExercisePart() {
        return exercisePart;
    }

    public void setExercisePart(String exercisePart) {
        this.exercisePart = exercisePart;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
