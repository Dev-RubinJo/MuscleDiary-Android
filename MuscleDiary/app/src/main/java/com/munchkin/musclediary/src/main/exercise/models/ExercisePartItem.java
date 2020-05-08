package com.munchkin.musclediary.src.main.exercise.models;

import java.util.ArrayList;

public class ExercisePartItem {

    private String exercisePartTitle;
    private ArrayList<ExerciseItem> exerciseItemList;

    public String getExercisePartTitle() {
        return exercisePartTitle;
    }

    public void setExercisePartTitle(String exercisePartTitle) {
        this.exercisePartTitle = exercisePartTitle;
    }

    public ArrayList<ExerciseItem> getExerciseItemList() {
        return exerciseItemList;
    }

    public void setExerciseItemList(ArrayList<ExerciseItem> exerciseItemList) {
        this.exerciseItemList = exerciseItemList;
    }
}
