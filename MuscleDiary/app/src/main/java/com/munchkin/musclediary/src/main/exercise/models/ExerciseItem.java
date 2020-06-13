package com.munchkin.musclediary.src.main.exercise.models;

import java.io.Serializable;

public class ExerciseItem implements Serializable {

    private String exerciseName;
    private String exercisePart;
    private int set;
    private int repeat;
    private int min;
    private int intensity;
    private double weight;

    public ExerciseItem(String exerciseName, String exercisePart) {
        this.exerciseName = exerciseName;
        this.exercisePart = exercisePart;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
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

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
