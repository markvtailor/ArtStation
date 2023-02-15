package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.model.toDomain
import com.markvtls.artstation.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: ImagesRepository
) {
    suspend operator fun invoke(): Flow<List<Image>> = flow {
        val favoriteImagesRaw = repository.getFavorites()
        val favoriteImages = favoriteImagesRaw.map { it.toDomain() }
        emit(favoriteImages)
    }
}