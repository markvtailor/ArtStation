package com.markvtls.artstation.workers

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.markvtls.artstation.MainActivity
import com.markvtls.artstation.domain.use_cases.GetImageByIdUseCase
import com.markvtls.artstation.domain.use_cases.GetNewImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltWorker
class NewImagesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val getNewImageUseCase: GetNewImageUseCase,
    private val getImageByIdUseCase: GetImageByIdUseCase
    ): CoroutineWorker(context, params) {


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        try {
            println("enqueueue")
            getNewImageUseCase().collect { newImage ->
                getImageByIdUseCase("1").collect { lastImage ->
                    if (newImage.trending != lastImage.trending) {
                        with(NotificationManagerCompat.from(context)) {
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                println(newImage.url)
                                println(lastImage.url)
                                notify(Random.nextInt(), NotificationsManager(context).builder.build())
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