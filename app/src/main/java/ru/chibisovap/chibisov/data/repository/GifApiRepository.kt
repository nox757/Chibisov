package ru.chibisovap.chibisov.data.repository

import ru.chibisovap.chibisov.data.api.GifApiService

class GifApiRepository(private val apiService: GifApiService) {

    suspend fun getRandomPost() = apiService.getRandomPost()
}