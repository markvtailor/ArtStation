package com.markvtls.artstation.domain.model

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity

data class Image (
    val id: String,
    val url: String,
    val title: String,
    val isMain: Boolean,
    val isFavorite: Boolean
        )


fun ImageResponseDto.toDomain(): Image {
    return Image(
        id = this.data.id,
        url = this.data.images.original.url,
        title = this.data.title,
        isMain = true,
        isFavorite = false
    )
}

fun ImageEntity.toDomain(): Image {
    return Image (
        id = id,
        url = url,
        title = title,
        isMain = isMain,
        isFavorite = isFavorite
            )
}