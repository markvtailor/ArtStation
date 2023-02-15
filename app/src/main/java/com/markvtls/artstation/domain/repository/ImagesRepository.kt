package com.markvtls.artstation.domain.repository

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.dto.Images
import com.markvtls.artstation.data.source.local.ImageEntity
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    suspend fun getLastTrendingGif(apiKey: String, limit: Int): ImageResponseDto

    suspend fun getRealm(): Flow<SingleQueryChange<ImageEntity>>
    suspend fun saveImage(id: String, url: String)

    suspend fun deleteImage(id: String)

    suspend fun getImageById(id: String): ImageEntity

    suspend fun getFavorites(): List<ImageEntity>

}