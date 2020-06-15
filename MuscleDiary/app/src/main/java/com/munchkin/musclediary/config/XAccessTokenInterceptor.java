package com.munchkin.musclediary.config;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.munchkin.musclediary.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class XAccessTokenInterceptor implements Interceptor {

    @Override
    @NonNull
    public Response intercept(@NonNull final Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String jwtToken = sSharedPreferences.getString("x-access-token", null);
        //final String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiMjAyMC0wNS0yOSAxNjoxMDozNiIsImlkIjoiaHNqMzIxIiwicHciOiJxMXExcTFxMSJ9.AkRUDgY50vHrQmV8RYJ_mkCGgRktpKXnYwQNN-tdhFE";

        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken);
        }
        return chain.proceed(builder.build());
    }
}
