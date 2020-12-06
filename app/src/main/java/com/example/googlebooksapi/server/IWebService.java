package com.example.googlebooksapi.server;

import com.example.googlebooksapi.models.JsonFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWebService {
    @GET("v1/volumes")
    Call<JsonFeed> getBookVolume(@Query("q") String queryParams, @Query("maxResults") String maxResults);
}
