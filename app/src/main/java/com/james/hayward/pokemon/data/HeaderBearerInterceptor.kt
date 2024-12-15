package com.james.hayward.pokemon.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderBearerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        // Add header if required
            .addHeader("Authorization", "Bearer ")
            .build()
        return chain.proceed(newRequest)
    }
}