package com.ocp.interventionapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ocp.interventionapp.R
import com.ocp.interventionapp.databinding.ActivityMainBinding
import com.ocp.interventionapp.ui.fragments.DashboardFragment
import com.ocp.interventionapp.ui.fragments.InterventionsFragment
import com.ocp.interventionapp.ui.fragments.EquipmentsFragment
import com.ocp.interventionapp.ui.fragments.StatisticsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
            binding.bottomNavigation.selectedItemId = R.id.navigation_dashboard
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    loadFragment(DashboardFragment())
                    true
                }
                R.id.navigation_interventions -> {
                    loadFragment(InterventionsFragment())
                    true
                }
                R.id.navigation_equipments -> {
                    loadFragment(EquipmentsFragment())
                    true
                }
                R.id.navigation_statistics -> {
                    loadFragment(StatisticsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
