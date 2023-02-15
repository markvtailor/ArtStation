package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.domain.repository.ImagesRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val repository: ImagesRepository
) {
    suspend operator fun invoke(id: String, url: String) {
        repository.saveImage(id, url)
    }
}