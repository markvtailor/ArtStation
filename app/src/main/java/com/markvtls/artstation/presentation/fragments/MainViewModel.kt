package com.markvtls.artstation.presentation.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.use_cases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewImage: GetNewImageUseCase,
    private val saveImage: SaveImageUseCase,
    private val deleteImage: DeleteImageUseCase,
    private val getImageById: GetImageByIdUseCase,
    private val getFavorites: GetFavoritesUseCase
) : ViewModel() {

    init {
        getImage()
    }


    val currentImage: MutableLiveData<Image> by lazy {
        MutableLiveData<Image>()
    }

    fun getImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewImage().collect {

                    saveImage("1",it.url)
                    currentImage.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loadLastImage()
            }

        }
    }



    fun deleteImageById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteImage(id)
        }
    }

    private suspend fun loadLastImage() {
        val lastImage = getImageById("1")
        println(lastImage)
        lastImage.collect {
            currentImage.postValue(it)
        }
    }
}