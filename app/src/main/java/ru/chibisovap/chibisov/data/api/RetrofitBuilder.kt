package ru.chibisovap.chibisov.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL: String = "https://developerslife.ru/"
    private const val TIMEOUT_SECONDS: Long = 30

    private val client = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()

    fun getGifClient(): GifApiService = retrofit.create(GifApiService::class.java)

}