package com.financeapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.financeapp.utils.SharedPreferencesInfo
import com.financeapp.viewmodels.SplashScreenViewModel
import com.financeapp.views.R
import kotlinx.android.synthetic.main.main_activity.*

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_activity)


    }

}
