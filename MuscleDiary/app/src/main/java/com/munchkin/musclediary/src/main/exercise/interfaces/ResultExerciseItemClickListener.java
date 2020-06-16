package com.munchkin.musclediary.src.main.exercise.interfaces;

import android.content.Intent;

import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

public interface ResultExerciseItemClickListener {
    void onResultItemClicked(Intent arrangeExerciseIntent, ExerciseItem clickedItem);
    void onExerciseDeleteClicked(int exerciseNo);
}
