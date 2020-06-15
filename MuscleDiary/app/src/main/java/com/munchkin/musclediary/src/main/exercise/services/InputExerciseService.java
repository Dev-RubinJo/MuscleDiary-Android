package com.munchkin.musclediary.src.main.exercise.services;

import com.munchkin.musclediary.src.main.exercise.interfaces.ExerciseRetrofitInterface;
import com.munchkin.musclediary.src.main.exercise.interfaces.InputExerciseActivityView;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseRequest;
import com.munchkin.musclediary.src.main.exercise.models.AddExerciseResponse;

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
}
