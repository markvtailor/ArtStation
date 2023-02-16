package com.markvtls.artstation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**Application class is required to use Hilt DI*/
@HiltAndroidApp
class MainApp: Application(), Configuration.Provider {

    /**WorkerFactory is necessary for DI Worker with Hilt */
    @Inject lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()



}