package com.example.googlebooksapi;

import com.example.googlebooksapi.models.JsonFeed;

public interface APIResponseListener {
    void onAPISuccessfulResponse(JsonFeed jsonFeed);
    void onAPIFailureResponse(String message);
}
