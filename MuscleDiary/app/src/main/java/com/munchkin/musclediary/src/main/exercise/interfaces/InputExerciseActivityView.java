package com.munchkin.musclediary.src.main.exercise.interfaces;

import com.munchkin.musclediary.src.main.exercise.models.ExerciseResult;

import java.util.ArrayList;

public interface InputExerciseActivityView {
    void addExerciseSuccess(int code, String message);
    void readExerciseSuccess(int code, String message, ArrayList<ExerciseResult> exerciseResults);
    void validateFailure(String message);
}
