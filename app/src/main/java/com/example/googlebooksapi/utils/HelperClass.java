package com.example.googlebooksapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class HelperClass {
    public static boolean isConnectedToInternet(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
