package com.markvtls.artstation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.markvtls.artstation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_fragment).navigateUp(appBarConfiguration)
    }
}