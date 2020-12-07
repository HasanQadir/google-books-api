package com.example.googlebooksapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.fragments.FetchJsonFragment;
import com.example.googlebooksapi.interfaces.FetchDataOnSuccessListener;

public class FetchDataActivity extends AppCompatActivity implements FetchDataOnSuccessListener{

    FetchJsonFragment fetchJsonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        showFetchJsonFragment();
    }

    private void showFetchJsonFragment(){
        fetchJsonFragment = FetchJsonFragment.newInstance();
        fetchJsonFragment.setFetchDataOnSuccessListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fetchData_frame_layout, fetchJsonFragment);
        ft.commit();
    }

    @Override
    public void onSuccessfulResponse() {
        startActivity(new Intent(FetchDataActivity.this, BookListActivity.class));
        finish();
    }
}