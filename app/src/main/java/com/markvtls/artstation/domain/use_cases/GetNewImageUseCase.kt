package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.data.Constants
import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.model.toDomain
import com.markvtls.artstation.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**Use this to get new image data from GiphyAPI*/
class GetNewImageUseCase @Inject constructor(
    private val repository: ImagesRepository
) {
    suspend operator fun invoke(): Flow<Image> = flow {
        val response = repository.getLastTrendingGif(Constants.API_KEY, Constants.LIMIT)
        val imageData = response.toDomain()
        emit(imageData)
    }
}