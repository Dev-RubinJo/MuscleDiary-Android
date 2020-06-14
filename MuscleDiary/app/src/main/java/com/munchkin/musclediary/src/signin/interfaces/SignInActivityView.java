package com.munchkin.musclediary.src.signin.interfaces;

import com.munchkin.musclediary.src.signin.models.SignInResponse;

public interface SignInActivityView {
    void SignInSuccess(int code, String message, SignInResponse.Jwt jwt);
    void SignUpSuccess(int code, String message);
    void validateFailure(String message);
}