package com.hds.labs.ids.hdslabs

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.hds.labs.ids.hdslabs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var pos = 0
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.toolbar.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navDrawer)
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavContainer.setupWithNavController(navController)


        binding.bottomNavContainer.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.homeFragmentMenu -> {
                    navController.navigate(R.id.homeFragmentMain)
                }
                R.id.locationsFragmentMenu -> {
                    navController.navigate(R.id.locationFragmentMain)
                }
                R.id.aboutFragmentMenu -> {
                    navController.navigate(R.id.aboutFragmentMain)
                }
                R.id.accountFragmentMenu -> {
                    navController.navigate(R.id.accountFragment2)
                }
            }
            true
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragmentMain -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                    binding.toolbar.root.visibility = View.VISIBLE
                }
                R.id.locationFragmentMain -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                    binding.toolbar.root.visibility = View.GONE
                }
                R.id.aboutFragmentMain -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                    binding.toolbar.root.visibility = View.GONE
                }
                R.id.accountFragment2 -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                    binding.toolbar.root.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavContainer.visibility = View.GONE
                    binding.toolbar.root.visibility = View.GONE
                }
            }
        }

    }

/*    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.main_navigation)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        finish()
        finishAffinity()

        val fragmentId = navController.currentDestination !!.id
        if (fragmentId == R.id.homeFragmentMain) {
            finish()
            finishAffinity()
        } else {
            navController.navigate(R.id.homeFragmentMain)
            //binding.bottomNavContainer.setc
            //binding.bottomNavContainer.setCurrentActiveItem(0)
        }
    }


}