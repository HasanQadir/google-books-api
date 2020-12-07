package com.example.googlebooksapi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.utils.HelperClass;
import com.example.googlebooksapi.utils.SharedPreferencesHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Checking Internet Connection and showing results accordingly
        if (SharedPreferencesHelper.getBookListData("bookList", "") == null
                && !HelperClass.isConnectedToInternet(this)){
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_LONG).show();
            finish();
        } else if (SharedPreferencesHelper.getBookListData("bookList", "") != null){
            startActivity(new Intent(SplashActivity.this, BookListActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(SplashActivity.this, FetchDataActivity.class));
            finish();
        }
    }
}