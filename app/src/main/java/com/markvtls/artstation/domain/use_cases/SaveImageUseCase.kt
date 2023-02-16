package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.repository.ImagesRepository
import javax.inject.Inject

/**Use this save an image to RealmDB*/
class SaveImageUseCase @Inject constructor(
    private val repository: ImagesRepository
) {
    suspend operator fun invoke(image: Image) {
        repository.saveImage(image.id, image.url, image.title, image.isMain, image.isFavorite)
    }
}