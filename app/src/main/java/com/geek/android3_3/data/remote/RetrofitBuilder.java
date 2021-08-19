package com.geek.android3_3.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static HerokuApi instance;

    private RetrofitBuilder() {
    }

    public static HerokuApi getInstance() {
        if (instance == null) {
            instance = createRetrofit();
        }
        return instance;
    }

    private static HerokuApi createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/posts/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HerokuApi.class);
    }
}
