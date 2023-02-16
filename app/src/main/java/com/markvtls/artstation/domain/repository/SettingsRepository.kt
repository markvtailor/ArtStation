package com.markvtls.artstation.domain.repository

import kotlinx.coroutines.flow.Flow



/**Repository for managing Settings */
interface SettingsRepository {

    suspend fun saveNotificationSettings(notifications: Boolean)

    fun getNotificationSettings(): Flow<Boolean>
}