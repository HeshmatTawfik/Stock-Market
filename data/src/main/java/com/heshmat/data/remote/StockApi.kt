package com.heshmat.data.remote

import retrofit2.http.Query
import  com.heshmat.data.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey:String = API_KEY,
    ):ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val API_KEY = BuildConfig.API_KEY
    }
}