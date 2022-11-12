package com.ksa.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
        navController = navHostFragment?.navController!!
        setupActionBarWithNavController(let { navController })

    }

    override fun onNavigateUp(): Boolean {
        //navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?)?.navController!!
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}