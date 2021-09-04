package ru.chibisovap.chibisov.data.repository

import ru.chibisovap.chibisov.data.model.GifModel

interface GifRepository {
    fun getNext() : GifModel;
    fun getPrev() : GifModel;
    fun get() : GifModel;
}