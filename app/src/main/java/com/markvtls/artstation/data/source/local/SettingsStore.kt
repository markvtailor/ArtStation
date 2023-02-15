package com.markvtls.artstation.data.source.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val SETTINGS = "SETTINGS"
private val NOTIFICATIONS = booleanPreferencesKey("notifications_setting")


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS
)
class SettingsStore(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveNotificationsSettingsToDataStore(notifications: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS] = notifications
        }
    }

    val notificationsSettingFlow: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[NOTIFICATIONS] ?: false
        }







}