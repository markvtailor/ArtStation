package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.model.toDomain
import com.markvtls.artstation.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**Use this to get last downloaded image*/
class GetLastImageUseCase @Inject constructor(
    private val repository: ImagesRepository
){
    suspend operator fun invoke(): Flow<Image> = flow {
        val image = repository.getMainImage().toDomain()
        emit(image)
    }
}