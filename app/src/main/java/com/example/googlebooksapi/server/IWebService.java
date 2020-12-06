package com.example.googlebooksapi.server;

import com.example.googlebooksapi.models.JsonFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IWebService {
    @GET("v1/volumes")
    Call<JsonFeed> getProfile();
}
