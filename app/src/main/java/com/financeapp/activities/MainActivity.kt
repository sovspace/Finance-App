package com.financeapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.financeapp.views.R
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivityNavigationHost) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(bottomNavigationView, navController)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,
        R.id.balanceHistoryFragment, R.id.settingsFragment, R.id.buyStocksFragment, R.id.stocksFragment))
        val toolbar = findViewById<Toolbar>(R.id.mainActivityToolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            if (destination.id == R.id.balanceHistoryFragment) {
                toolbar.inflateMenu(R.menu.balance_history_toolbar)
            } else {
                toolbar.menu.clear()
            }


        }
    }

}
