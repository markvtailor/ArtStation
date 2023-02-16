package com.markvtls.artstation.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.markvtls.artstation.domain.repository.SettingsRepository
import com.markvtls.artstation.domain.use_cases.GetLastImageUseCase
import com.markvtls.artstation.domain.use_cases.GetNewImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random


/**This worker is responsible for notifying user about new trending image*/
@HiltWorker
class NewImagesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val getNewImageUseCase: GetNewImageUseCase,
    private val getLastImageUseCase: GetLastImageUseCase,
    private val settings: SettingsRepository
    ): CoroutineWorker(context, params) {


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        try {
            settings.getNotificationSettings().collect {
                if (it) {
                    getNewImageUseCase().collect { newImage ->
                        getLastImageUseCase().collect { lastImage ->
                            println(newImage)
                            println(lastImage)
                            if (newImage.id != lastImage.id) {
                                with(NotificationManagerCompat.from(context)) {
                                    if (ActivityCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.POST_NOTIFICATIONS
                                        ) == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        notify(Random.nextInt(), NotificationsManager(context).builder.build())
                                    }

                                }
                            }
                        }
                    }
                }
            }




            } catch (e: Exception) {
                e.printStackTrace()
            }

        return Result.success()
    }


    companion object {
        const val WORK_NAME = "ArtStation check for updates"
    }
    }