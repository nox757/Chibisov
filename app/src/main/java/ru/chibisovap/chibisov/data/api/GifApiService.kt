package ru.chibisovap.chibisov.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.chibisovap.chibisov.data.model.GifModel

interface GifApiService {

    @GET("random?json=true")
    suspend fun getRandomPost(): Response<GifModel>
}