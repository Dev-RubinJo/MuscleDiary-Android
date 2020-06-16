package com.munchkin.musclediary.src.main.exercise.interfaces;

import com.munchkin.musclediary.src.main.exercise.models.AddExerciseRequest;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseResponse;
import com.munchkin.musclediary.src.main.exercise.models.DeleteExerciseRequest;
import com.munchkin.musclediary.src.main.exercise.models.DeleteExerciseResponse;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ExerciseRetrofitInterface {
    @POST("/user/exercise")
    Call<AddExerciseResponse> postAddExercise(@Body AddExerciseRequest addExerciseRequest);

    @GET("/user/exercise")
    Call<ExerciseListResponse> getReadExercise(@Query("recordDate")String recordDate);

    @HTTP(method = "DELETE", path = "/user/exercise", hasBody = true)
    Call<DeleteExerciseResponse> deleteExercise(@Body DeleteExerciseRequest deleteExerciseRequest);
}
