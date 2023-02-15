package com.markvtls.artstation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.markvtls.artstation.databinding.ActivityMainBinding
import com.markvtls.artstation.presentation.fragments.SettingsViewModel
import com.markvtls.artstation.workers.ImageUpdateWorker
import com.markvtls.artstation.workers.NewImagesWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

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

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_fragment).navigateUp(appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        runUpdateWorker()
        WorkManager.getInstance(applicationContext).cancelUniqueWork(NewImagesWorker.WORK_NAME)
    }
    override fun onPause() {
        super.onPause()
        WorkManager.getInstance(applicationContext).cancelUniqueWork(ImageUpdateWorker.WORK_NAME)
            settingsViewModel.notificationsSettings.observe(this@MainActivity, Observer { notificationSettings ->
                if (notificationSettings) {
                    runNotificationsWorker()
                }
            })
    }
    private fun runNotificationsWorker() {
        val newImagesRequest = PeriodicWorkRequestBuilder<NewImagesWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            NewImagesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            newImagesRequest
        )
    }

    private fun runUpdateWorker() {
        val updateImageRequest = PeriodicWorkRequestBuilder<ImageUpdateWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            ImageUpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateImageRequest
        )
    }
}