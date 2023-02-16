package com.markvtls.artstation.domain.model

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity




/**Domain model for Image. */
data class Image (
    val id: String,
    val url: String,
    val title: String,
    val isMain: Boolean,
    val isFavorite: Boolean
        )




/**Converting API response into Image */
fun ImageResponseDto.toDomain(): Image {
    return Image(
        id = this.data.first().id,
        url = this.data.first().images.original.url,
        title = this.data.first().title,
        isMain = true,
        isFavorite = false
    )
}



/**Converting RealmDB Object into Image */
fun ImageEntity.toDomain(): Image {
    return Image (
        id = id,
        url = url,
        title = title,
        isMain = isMain,
        isFavorite = isFavorite
            )
}