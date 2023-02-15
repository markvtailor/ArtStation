package com.markvtls.artstation.di

import com.markvtls.artstation.domain.repository.ImagesRepository
import com.markvtls.artstation.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
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
        GetImageByIdUseCase(repository)

    @Provides
    fun provideGetFavoritesUseCase(repository: ImagesRepository) =
        GetFavoritesUseCase(repository)
}