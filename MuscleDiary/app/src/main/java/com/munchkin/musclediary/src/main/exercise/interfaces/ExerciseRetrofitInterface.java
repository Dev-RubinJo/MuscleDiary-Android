package com.munchkin.musclediary.src.main.exercise.interfaces;

import com.munchkin.musclediary.src.main.exercise.models.AddExerciseRequest;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ExerciseRetrofitInterface {
    @POST("/user/exercise")
    Call<AddExerciseResponse> postAddExercise(@Body AddExerciseRequest addExerciseRequest);
}
