package com.datalayer

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

inline fun <reified T> Retrofit.Builder.createCoroutineAdapter(url: String): T {

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val builder = this
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .baseUrl(url)
        .client(client)
        .build()
    return builder.create(T::class.java)
}