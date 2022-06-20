package com.fitlife.fitlife.dashbaord

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.fitlife.fitlife.R
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class DashbaordActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    private lateinit var dao: FitLifeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
         drawerLayout = findViewById<DrawerLayout>(R.id.drawerlayout)
        navigationView = findViewById(R.id.navigation_view)
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        NavigationUI.setupActionBarWithNavController(this,navController)


        NavigationUI.setupWithNavController(navigationView,navController)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu_24)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }

    override fun onBackPressed() {
        if (navController.currentDestination == null
            || navController.currentDestination!!.id in setOf(
                R.id.exerciseTrackerFragment,
                R.id.exerciseCategories,
                R.id.aboutFragment)
        ) {
            super.onBackPressed()
        } else {
            navController.navigateUp()
        }
    }
}