package com.example.googlebooksapi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.googlebooksapi.models.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesHelper {
    private static SharedPreferences dataPreferences;
    private static Context context;

    public static void init(Context context) {
        if (context == null)
            Log.e("SharedPreferencesHelper", "Init with null context.");
        if (SharedPreferencesHelper.context != null) {
            Log.e("SharedPreferencesHelper", "Init was already called.");
            return;
        }
        SharedPreferencesHelper.context = context;
        dataPreferences = context.getSharedPreferences("data", Activity.MODE_PRIVATE);
    }

    public static ArrayList<Book> getBookListData(String key, String defaultValue) {
        Gson gson = new Gson();
        String json = dataPreferences.getString("key", defaultValue);
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> bookList = gson.fromJson(json, type);
        return bookList;
    }

    public static void putBookListData(String key, ArrayList<Book> bookList) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(bookList);
        dataPreferences.edit().putString("key", jsonList).commit();
    }
}

