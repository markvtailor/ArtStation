package com.markvtls.artstation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.markvtls.artstation.domain.use_cases.GetNewImageUseCase
import com.markvtls.artstation.domain.use_cases.SaveImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


/**This worker is responsible for updating main image when the App is ON*/
@HiltWorker
class ImageUpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val getNewImageUseCase: GetNewImageUseCase,
    private val saveImageUseCase: SaveImageUseCase
): CoroutineWorker(context, params)  {
    override suspend fun doWork(): Result {
        getNewImageUseCase().collect {image ->
            saveImageUseCase(image)
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "ArtStation live updates"
    }
}