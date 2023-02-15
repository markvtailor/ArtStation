package com.markvtls.artstation.data.repository

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity
import com.markvtls.artstation.data.source.remote.GiphyApiService
import com.markvtls.artstation.domain.repository.ImagesRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApiService,
    private val realm: Realm
): ImagesRepository {
    override suspend fun getLastTrendingGif(apiKey: String, limit: Int): ImageResponseDto {
        return giphyApi.getLastTrendingGif(apiKey, limit)
    }



    override suspend fun saveImage(id: String, url: String) {
        val image = ImageEntity().apply {
            this.id = id
            this.url = url
        }
        realm.write {
            val existingImage = this.query<ImageEntity>("id =$0", id).first().find()

            if (existingImage != null) {
                existingImage.url = image.url
            } else {
                val savedImage = copyToRealm(image)
            }

        }
    }

    override suspend fun deleteImage(id: String) {
        realm.write {
            val query = realm.query<ImageEntity>("id =$0", id).find()
            delete(query)
        }
    }

    override suspend fun getImageById(id: String): ImageEntity {
        val lastImage = realm.query<ImageEntity>("id = $0", id).find().first()
        return lastImage
    }

    override suspend fun getFavorites(): List<ImageEntity> {
        val favoriteImages = realm.query<ImageEntity>("isFavorite = $0", true).find()
        return favoriteImages
    }


}