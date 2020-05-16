package com.munchkin.musclediary.src.signin.interfaces;

import com.munchkin.musclediary.src.signin.models.SignInResponse;

public interface SignInActivityView {
    void validateSuccess(int code, String message, SignInResponse.Jwt jwt);
    void validateFailure(String message);
}