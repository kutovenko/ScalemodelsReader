package com.blogspot.alexeykutovenko.scalemodelsreader.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiFactory {

    public static ScalemodelsApi create() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String BASE_URL = "http://scalemodels.ru/modules/calendar/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ScalemodelsApi.class);
    }
}
