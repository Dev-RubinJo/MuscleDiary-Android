package com.munchkin.musclediary.src.main.exercise.models;

public class ExerciseItem {

    private String execiseName;
    private String description;
    private int set;
    private int repeat;
    private double weight;

    public ExerciseItem(String execiseName, String description) {
        this.execiseName = execiseName;
        this.description = description;
    }

    public String getExeciseName() {
        return execiseName;
    }

    public void setExeciseName(String execiseName) {
        this.execiseName = execiseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
