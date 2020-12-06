package com.example.googlebooksapi;

import android.app.Application;

import com.example.googlebooksapi.utils.SharedPreferencesHelper;

public class BookAPIApplication extends Application {
    public static BookAPIApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        SharedPreferencesHelper.init(this);

    }
}
