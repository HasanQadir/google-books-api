package com.example.googlebooksapi;

import android.app.Application;

public class BookAPIApplication extends Application {
    public static BookAPIApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
