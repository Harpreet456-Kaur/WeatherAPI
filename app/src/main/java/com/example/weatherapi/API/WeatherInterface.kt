package com.example.weatherapi.API

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryName

interface WeatherInterface {
    //lat=30.6995988&lon=76.6927953&appid=90c5a8ba8fb9ff8e2ddb6495277008be
    @POST("data/2.5/weather")
    fun postData(
        @Query("lat") lat: String? = null,
        @Query("lon") lon: String? = null,
        @Query("appid") appid: String? = null
    ): Call<WeatherData>
}