package com.heshmat.data.remote

import retrofit2.http.Query
import  com.heshmat.data.BuildConfig
import com.heshmat.data.remote.dto.company_info.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings():ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
    ): CompanyInfoDto

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val API_KEY = BuildConfig.API_KEY
    }
}
