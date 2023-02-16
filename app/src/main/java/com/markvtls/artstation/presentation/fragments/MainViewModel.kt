package com.markvtls.artstation.presentation.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.artstation.data.source.local.ImageEntity
import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.model.toDomain
import com.markvtls.artstation.domain.repository.ImagesRepository
import com.markvtls.artstation.domain.use_cases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedObject
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewImage: GetNewImageUseCase,
    private val saveImage: SaveImageUseCase,
    private val deleteImage: DeleteImageUseCase,
    private val getImageById: GetLastImageUseCase,
    private val getFavorites: GetFavoritesUseCase,
    private val addImageToFavorites: AddImageToFavoritesUseCase,
    private val repository: ImagesRepository
) : ViewModel() {


    sealed class Event {
        object LoadingError: Event()
        data class ShowSnackBar(val text: String): Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        getImage()
        getFavoriteImages()
    }

    val favorites: MutableLiveData<List<Image>> by lazy {
        MutableLiveData<List<Image>>()
    }
    val currentImage: MutableLiveData<Image> by lazy {
        MutableLiveData<Image>()
    }




    private fun getImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewImage().collect {image ->
                    saveImage(image)
                    currentImage.postValue(image)
                }
            } catch (e: Exception) {
                loadLastImage()
                notifyAboutError()
                e.printStackTrace()
            }
        }
    }

    private fun notifyAboutError() {
        viewModelScope.launch {
            eventChannel.send(Event.LoadingError)
        }
    }

    fun deleteImageById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteImage(id)
        }
    }

    private suspend fun loadLastImage() {
        getImageById().collect {
            currentImage.postValue(it)
        }
    }

    fun addNewImageToFavorites(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            addImageToFavorites(image)
        }
    }

    private fun getFavoriteImages() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavorites().collect {
                favorites.postValue(it)
            }
        }
    }

    val mainImageChanges = viewModelScope.launch(Dispatchers.Default) {
        repository.subscribeToMainChanges().collect { changes: SingleQueryChange<ImageEntity> ->
            when(changes) {
                is UpdatedObject -> {
                    currentImage.postValue(changes.obj.toDomain())
                }
                else -> changes.obj
            }
        }
    }

    val favoritesChanges = viewModelScope.launch(Dispatchers.Default) {
        repository.subscribeToFavoriteChanges().collect { changes: ResultsChange<ImageEntity> ->
            when(changes) {
                is UpdatedResults -> {
                    favorites.postValue(changes.list.map { it.toDomain() })
                }
                else -> changes.list
            }

        }
    }




}