package com.markvtls.artstation.domain.repository

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity
import com.markvtls.artstation.domain.model.Image
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    suspend fun getLastTrendingGif(apiKey: String, limit: Int): ImageResponseDto

    suspend fun subscribeToMainChanges(): Flow<SingleQueryChange<ImageEntity>>

    suspend fun subscribeToFavoriteChanges(): Flow<ResultsChange<ImageEntity>>
    suspend fun saveImage(id: String, url: String, title: String, isMain: Boolean, isFavorite: Boolean)

    suspend fun deleteImage(id: String)

    suspend fun getMainImage(): ImageEntity
    suspend fun addImageToFavorites(image: Image)
    suspend fun getFavorites(): List<ImageEntity>

}