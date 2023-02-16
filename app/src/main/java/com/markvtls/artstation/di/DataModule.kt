package com.markvtls.artstation.di

import android.content.Context
import com.markvtls.artstation.data.repository.ImagesRepositoryImpl
import com.markvtls.artstation.data.repository.SettingsRepositoryImpl
import com.markvtls.artstation.data.source.local.ImageEntity
import com.markvtls.artstation.data.source.local.SettingsStore
import com.markvtls.artstation.data.source.remote.GiphyApiService
import com.markvtls.artstation.domain.repository.ImagesRepository
import com.markvtls.artstation.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton




/**Data components DI. */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    fun provideRealmConfiguration(): RealmConfiguration = RealmConfiguration.create(schema = setOf(ImageEntity::class))

    @Provides
    @Singleton
    fun provideRealmDatabase(configuration: RealmConfiguration): Realm = Realm.open(configuration)

    @Provides
    @Singleton
    fun provideImagesRepository(giphyApi: GiphyApiService, realm: Realm): ImagesRepository =
        ImagesRepositoryImpl(giphyApi, realm)

    @Provides
    @Singleton
    fun provideSettingsStore(@ApplicationContext context: Context): SettingsStore =
        SettingsStore(context)

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsStore: SettingsStore): SettingsRepository =
        SettingsRepositoryImpl(settingsStore)


}