package com.markvtls.artstation.presentation.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.artstation.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val notificationsSettings: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        getNotificationsSettings()
    }

    fun saveNotificationsSettings(notifications: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveNotificationSettings(notifications)
        }
    }


    fun getNotificationsSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getNotificationSettings().collect {
                notificationsSettings.postValue(it)
            }
        }
    }
}