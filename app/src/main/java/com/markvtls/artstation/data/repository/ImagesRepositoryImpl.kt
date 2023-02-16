package com.markvtls.artstation.data.repository

import com.markvtls.artstation.data.dto.ImageResponseDto
import com.markvtls.artstation.data.source.local.ImageEntity
import com.markvtls.artstation.data.source.remote.GiphyApiService
import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.domain.repository.ImagesRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



/**ImagesRepository implementation */
class ImagesRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApiService,
    private val realm: Realm
): ImagesRepository {
    override suspend fun getLastTrendingGif(apiKey: String, limit: Int): ImageResponseDto {
        return giphyApi.getLastTrendingGif(apiKey, limit)
    }

    override suspend fun subscribeToMainChanges(): Flow<SingleQueryChange<ImageEntity>> {
        return realm.query(ImageEntity::class, "isMain == true").first().asFlow()
    }

    override suspend fun subscribeToFavoriteChanges(): Flow<ResultsChange<ImageEntity>> {
        return realm.query(ImageEntity::class, "isFavorite == true").asFlow()
    }

    override suspend fun saveImage(id: String, url: String, title: String, isMain: Boolean, isFavorite: Boolean) {
        val image = ImageEntity().apply {
            this.id = id
            this.url = url
            this.isMain = isMain
            this.isFavorite = isFavorite
            this.title = title
        }
        realm.write {
            val existingImage = this.query<ImageEntity>("isMain == true").first().find()

            if (existingImage != null) {
                existingImage.id = id
                existingImage.url = image.url
                existingImage.title = image.title
            } else {
                val savedImage = copyToRealm(image)
            }

        }
    }

    override suspend fun deleteImage(id: String) {
        realm.write {
            val query = this.query<ImageEntity>("id = $0",id)
            delete(query)
        }
    }

    override suspend fun getMainImage(): ImageEntity {
        val mainImage = realm.query<ImageEntity>("isMain == true").find()
        if (mainImage.isNotEmpty()) {
            return mainImage.first()
        }
        return ImageEntity()
    }

    override suspend fun addImageToFavorites(image: Image) {
        val newFavorite = ImageEntity().apply {
            this.id = image.id
            this.url = image.url
            this.isFavorite = true
            this.title = image.title
            this.isMain = false
        }
            realm.write {
                val favoriteImage = this.query<ImageEntity>("id =$0", image.id).first().find()

                if (favoriteImage != null) {
                    favoriteImage.url = image.url
                    favoriteImage.isFavorite = true
                    favoriteImage.isMain = false
                    favoriteImage.title = image.title
                } else {
                    val savedImage = copyToRealm(newFavorite)
                }

            }
        }


    override suspend fun getFavorites(): List<ImageEntity> {
        val favorites = realm.query<ImageEntity>("isFavorite == true").find()
        return favorites.ifEmpty { listOf() }
    }


}