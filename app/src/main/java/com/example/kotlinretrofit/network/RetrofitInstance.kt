package com.example.kotlinretrofit.network

import com.example.kotlinretrofit.Utils.Apis.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{

        fun getRetrofitInstance(): Retrofit{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level= HttpLoggingInterceptor.Level.BODY

            val httpClient= OkHttpClient.Builder()
            httpClient.addInterceptor(loggingInterceptor)


            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create()).
                build()
        }
    }
}