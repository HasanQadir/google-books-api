package com.example.googlebooksapi.server;

import com.example.googlebooksapi.BookAPIApplication;
import com.example.googlebooksapi.R;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public final static String API_BASE_URL = BookAPIApplication.application.getApplicationContext().getString(R.string.API_BASE_URL);
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static HashMap<Class<?>, Object> Services = new HashMap<Class<?>, Object>();

    public static <T> void setService(Class<T> klass, T thing) {
        Services.put(klass, thing);
    }

    public static <T> T getService(Class<T> serviceClass) {

        T service = serviceClass.cast(Services.get(serviceClass));
        if (service == null) {
            service = createService(serviceClass);
            setService(serviceClass, service);
        }
        return service;
    }

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor( new HeaderInterceptor());
        httpClient.readTimeout(2, TimeUnit.MINUTES);
        httpClient.writeTimeout(2, TimeUnit.MINUTES);
        httpClient.connectTimeout(2, TimeUnit.MINUTES);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}

