package ru.chibisovap.chibisov.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chibisovap.chibisov.data.repository.GifApiRepository
import ru.chibisovap.chibisov.data.api.RetrofitBuilder
import ru.chibisovap.chibisov.data.model.GifModel
import ru.chibisovap.chibisov.utils.Resource

class GifViewModel() : ViewModel() {

    private val gifApiRepository: GifApiRepository =
        GifApiRepository(RetrofitBuilder.getGifClient())

    private val gif = MutableLiveData<Resource<GifModel>>()

    private var position: Int = -1
    private val gifModelList: MutableList<GifModel> = mutableListOf()

    init {
        fetchGif()
    }

    private fun fetchGif() {
        viewModelScope.launch {
            gif.postValue(Resource.loading(null))
            try {
                val gifApiResponse = gifApiRepository.getRandomPost()
                if (gifApiResponse.isSuccessful && gifApiResponse.body() != null) {
                    gif.postValue(Resource.success(gifApiResponse.body()))
                    gifModelList.add(gifApiResponse.body()!!)
                    position += 1
                } else {
                    gif.postValue(Resource.error("No success data", null))
                }
            } catch (e: Exception) {
                gif.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getGif(): LiveData<Resource<GifModel>> {
        return gif
    }

    fun getNextGif(): LiveData<Resource<GifModel>> {
        if (position >= gifModelList.size - 1) {
            fetchGif()
            return gif
        }
        position += 1
        gif.postValue(Resource.success(gifModelList[position]))
        return gif
    }

    fun getPrevGif(): LiveData<Resource<GifModel>> {
        position -= 1
        if (position < 0) {
            position = 0
            return gif
        }
        gif.postValue(Resource.success(gifModelList[position]))
        return gif
    }
}