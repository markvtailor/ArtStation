package com.markvtls.artstation.data.repository

import com.markvtls.artstation.data.source.local.SettingsStore
import com.markvtls.artstation.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



/**Settings repository implementation. */
class SettingsRepositoryImpl @Inject constructor(
    private val settings: SettingsStore
) : SettingsRepository {
    override suspend fun saveNotificationSettings(notifications: Boolean) {
        settings.saveNotificationsSettingsToDataStore(notifications)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return settings.notificationsSettingFlow
    }


}