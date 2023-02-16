package com.markvtls.artstation.domain.use_cases

import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.repository.ImagesRepository
import javax.inject.Inject



/**Use this to mark an image as favorite*/
class AddImageToFavoritesUseCase @Inject constructor(
    private val repository: ImagesRepository
){
    suspend operator fun invoke(image: Image) {
        repository.addImageToFavorites(image)
    }
}