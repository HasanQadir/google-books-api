package com.example.googlebooksapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class HelperClass {
    public static boolean isConnectedToInternet(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static String queryParamFormatter(String volumeName, String authorName){
        volumeName = volumeName.trim().replaceAll(" ", "+");
        authorName = authorName.trim().replaceAll(" ", "+");
        String queryParams = volumeName + "+" + Constants.AUTHOR_TAG + authorName;
        return queryParams;
    }
}
