package com.heshmat.data.remote

import retrofit2.http.Query
import  com.heshmat.data.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey:String,
    ):ResponseBody

    companion object {
        const val BASE_URL = "alphavantage.co"
        const val API_KEY = BuildConfig.API_KEY
    }
}