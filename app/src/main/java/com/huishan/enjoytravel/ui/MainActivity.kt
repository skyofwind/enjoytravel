package com.huishan.enjoytravel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.util.StatusBarUtils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        setContentView(R.layout.activity_main)
        StatusBarUtils.setTransparent(this)
        StatusBarUtils.setTextDark(this, true)
        setNavHost()
    }

    private fun setNavHost() {
        navigationView = findViewById(R.id.navigation_view)
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
        navController = host.navController
        val configuration: AppBarConfiguration =
            AppBarConfiguration.Builder(navigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(navigationView, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.home_fragment_dest
                || destination.id == R.id.operation_manager_fragment_dest
                || destination.id == R.id.operational_statistics_fragment_dest
                || destination.id == R.id.personal_fragment_dest
            ) {
                runOnUiThread { navigationView.visibility = View.VISIBLE }
            } else {
                runOnUiThread { navigationView.visibility = View.GONE }
            }
        }
        /*设置一个空的menuItem重选事件，防止点击当前item重复导航*/
        navigationView.setOnNavigationItemReselectedListener { item ->

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.navigation_view).navigateUp()
    }

    override fun onBackPressed() {
        when(BikeApplication.fragmentBackStatus) {
            FragmentBackStatus.EXIT_APP -> {
                exitApp()
            }
            FragmentBackStatus.NONE -> {
                super.onBackPressed()
            }
        }
    }

    var exitTime: Long = 0
    fun exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, getString(R.string.tip_exit_app), Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}