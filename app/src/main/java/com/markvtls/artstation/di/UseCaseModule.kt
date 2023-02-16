package com.markvtls.artstation.di

import com.markvtls.artstation.domain.repository.ImagesRepository
import com.markvtls.artstation.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton




/**UseCases DI. */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetNewImageUseCase(repository: ImagesRepository) =
        GetNewImageUseCase(repository)

    @Provides
    fun provideSaveImageUseCase(repository: ImagesRepository) =
        SaveImageUseCase(repository)

    @Provides
    fun provideDeleteImageUseCase(repository: ImagesRepository) =
        DeleteImageUseCase(repository)

    @Provides
    fun provideGetImageByIdUseCase(repository: ImagesRepository) =
        GetLastImageUseCase(repository)

    @Provides
    fun provideGetFavoritesUseCase(repository: ImagesRepository) =
        GetFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideAddImageToFavoritesUseCase(repository: ImagesRepository) =
        AddImageToFavoritesUseCase(repository)
}