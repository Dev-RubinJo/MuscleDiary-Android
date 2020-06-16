package com.munchkin.musclediary.src.main.exercise.services;

import com.munchkin.musclediary.src.main.exercise.interfaces.ExerciseRetrofitInterface;
import com.munchkin.musclediary.src.main.exercise.interfaces.InputExerciseActivityView;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseRequest;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseResponse;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseListResponse;
import com.munchkin.musclediary.src.main.exercise.models.ExerciseResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.munchkin.musclediary.src.ApplicationClass.getRetrofit;

public class InputExerciseService {
    final InputExerciseActivityView mInputExerciseActivityView;

    public InputExerciseService(InputExerciseActivityView mInputExerciseActivityView) {
        this.mInputExerciseActivityView = mInputExerciseActivityView;
    }

    public void postAddExercise(final AddExerciseRequest addExerciseRequest){
        final ExerciseRetrofitInterface exerciseRetrofitInterface = getRetrofit().create(ExerciseRetrofitInterface.class);

        exerciseRetrofitInterface.postAddExercise(addExerciseRequest).enqueue(new Callback<AddExerciseResponse>() {
            @Override
            public void onResponse(Call<AddExerciseResponse> call, Response<AddExerciseResponse> response) {
                final AddExerciseResponse addExerciseResponse = response.body();
                if(addExerciseResponse == null){
                    mInputExerciseActivityView.validateFailure("null");
                    return;
                }

                mInputExerciseActivityView.addExerciseSuccess(addExerciseResponse.getCode(),
                        addExerciseResponse.getMessage());
            }

            @Override
            public void onFailure(Call<AddExerciseResponse> call, Throwable t) {
                mInputExerciseActivityView.validateFailure("null");
            }
        });
    }

    public void getReadExercise(final String recordDate){
        final ExerciseRetrofitInterface exerciseRetrofitInterface = getRetrofit().create(ExerciseRetrofitInterface.class);

        exerciseRetrofitInterface.getReadExercise(recordDate).enqueue(new Callback<ExerciseListResponse>() {
            @Override
            public void onResponse(Call<ExerciseListResponse> call, Response<ExerciseListResponse> response) {
                final ExerciseListResponse exerciseListResponse = response.body();
                if(exerciseListResponse == null){
                    mInputExerciseActivityView.validateFailure("null");
                    return;
                }
                    mInputExerciseActivityView.readExerciseSuccess(exerciseListResponse.getCode(),
                            exerciseListResponse.getMessage(),exerciseListResponse.getResult());
            }

            @Override
            public void onFailure(Call<ExerciseListResponse> call, Throwable t) {
                mInputExerciseActivityView.validateFailure("null");
            }
        });
    }
}
