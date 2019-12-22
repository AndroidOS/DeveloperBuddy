package com.casa.azul.dogs.model

import com.example.developerbuddy.model.RootObject
import io.reactivex.Single
import retrofit2.http.GET

interface SOApi {
    @GET("recipients?page=1&pagesize=100&fromdate=1567296000&todate=1576972800&site=stackoverflow")
    fun getSOItems(): Single<RootObject>
}