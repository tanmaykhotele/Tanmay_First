package com.tanmay.weatherforcaster

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
        fun getWeather(
        @Query("q") city:String,
        @Query("appid") aphid:String,
        @Query("units") units:String,
        ) : Call<ResponseDataClass>
}