package com.ubaya.uts_anmp_160420098.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ubaya.uts_anmp_160420098.R
import com.ubaya.uts_anmp_160420098.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.HostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val btmNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        btmNav.setupWithNavController(navController)

        btmNav.setOnNavigationItemSelectedListener { nav ->
            when (nav.itemId) {
                R.id.heroListFragment -> {
                    navController.navigate(R.id.heroListFragment)
                    true
                }
                R.id.heroDetailFragment -> {
                    if (viewModel.visited.value != null) {
                        navController.navigate(R.id.heroDetailFragment)
                    }
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            btmNav.visibility = if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) View.GONE else View.VISIBLE
        }
    }
}
