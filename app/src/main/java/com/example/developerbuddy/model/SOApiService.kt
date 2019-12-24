package com.example.developerbuddy.model


import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SOApiService {

    private val BASE_URL = "https://api.stackexchange.com/2.2/badges/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SOApi::class.java)

    fun getSOItems(): Single<RootObject> {
        return api.getSOItems()
    }
}