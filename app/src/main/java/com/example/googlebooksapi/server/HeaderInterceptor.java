package com.example.googlebooksapi.server;

import android.app.Application;

import com.example.googlebooksapi.BookAPIApplication;
import com.example.googlebooksapi.R;
import com.example.googlebooksapi.utils.HelperClass;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Application app = BookAPIApplication.application;
        okhttp3.Request request = chain.request();
        request = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
        okhttp3.Response response = chain.proceed(request);
        return response;
    }
}
