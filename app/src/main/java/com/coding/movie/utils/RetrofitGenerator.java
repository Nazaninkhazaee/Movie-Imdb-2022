package com.coding.movie.utils;

import com.coding.movie.constans.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenerator {
    public static <S> S create(Class<S> serviceClass ) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)

                .readTimeout(30, TimeUnit.SECONDS)

                .connectTimeout(30, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Constants.BASE_URL)

                .client(client)

                .addConverterFactory(GsonConverterFactory.create())

                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .build();

        return retrofit.create(serviceClass);

    }
}
