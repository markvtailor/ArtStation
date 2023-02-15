package com.markvtls.artstation.domain.model

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity

data class Image (
    val id: String,
    val url: String
        )


fun ImageResponseDto.toDomain(): Image {
    return Image(
        id = this.data.first().id,
        url = this.data.first().images.original.url
    )
}

fun ImageEntity.toDomain(): Image {
    return Image (
        id = id,
        url = url
            )
}