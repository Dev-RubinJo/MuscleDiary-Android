package com.munchkin.musclediary.src.main.exercise.interfaces;

import android.content.Intent;

import com.munchkin.musclediary.src.main.exercise.models.ExerciseItem;

public interface ResultExerciseItemClickListener {
    public void onResultItemClicked(Intent arrangeExerciseIntent, ExerciseItem clickedItem);
}
