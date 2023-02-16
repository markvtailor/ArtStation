package com.markvtls.artstation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.markvtls.artstation.databinding.ActivityMainBinding
import com.markvtls.artstation.workers.ImageUpdateWorker
import com.markvtls.artstation.workers.NewImagesWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


/**App' Main Activity*/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _appBarConfiguration: AppBarConfiguration? = null
    private val appBarConfiguration get() = _appBarConfiguration!!

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment? ?: return
        val navController = navHostFragment.navController


        val drawer = binding.drawerLayout
        val navigationView = binding.navView

        navigationView.setupWithNavController(navController)


        _appBarConfiguration = AppBarConfiguration(setOf(
            R.id.mainFragment,
            R.id.favoritesFragment,
            R.id.settingsFragment
        ), drawer)


        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        /**Run ImageUpdateWorker*/
        runUpdateWorker()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_fragment).navigateUp(appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        /**Stop NotificationsWorker*/
        WorkManager.getInstance(applicationContext).cancelUniqueWork(NewImagesWorker.WORK_NAME)

    }
    override fun onStop() {
        super.onStop()
        /**Stop ImageUpdateWorker and run NotificationsWorker*/
        WorkManager.getInstance(applicationContext).cancelUniqueWork(ImageUpdateWorker.WORK_NAME)
        runNotificationsWorker()
    }


    /**Use this to run NotificationsWorker*/
    private fun runNotificationsWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val newImagesRequest = PeriodicWorkRequestBuilder<NewImagesWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            NewImagesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            newImagesRequest
        )
    }

    /**Use this to run ImageUpdateWorker*/
    private fun runUpdateWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateImageRequest = PeriodicWorkRequestBuilder<ImageUpdateWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            ImageUpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateImageRequest
        )
    }
}