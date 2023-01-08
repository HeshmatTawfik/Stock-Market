package com.heshmat.data.di

import com.heshmat.data.remote.StockApi
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val url = originalUrl.newBuilder()
            .addQueryParameter("apikey", StockApi.API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}