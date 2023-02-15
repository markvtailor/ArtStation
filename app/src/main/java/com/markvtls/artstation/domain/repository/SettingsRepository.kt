package com.markvtls.artstation.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveNotificationSettings(notifications: Boolean)

    fun getNotificationSettings(): Flow<Boolean>
}