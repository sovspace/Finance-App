package com.financeapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.financeapp.activities.MainActivity
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.ServerViewModelFactory
import com.financeapp.utils.SharedPreferencesInfo
import com.financeapp.viewmodels.SplashScreenViewModel
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.ServiceGenerator


class SplashScreenFragment: Fragment() {

    lateinit var splashScreenFragment: SplashScreenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.splash_screen_fragment, container, false)

        val intent = Intent(requireActivity(), MainActivity::class.java)

        val navigationController = findNavController()
        val action = SplashScreenFragmentDirections.actionNavSplashScreenFragmentToNavLoginFragment()

        val authenticationServer = ServiceGenerator.createService(AuthenticationService::class.java)
        splashScreenFragment = ViewModelProvider(this,
            ServerViewModelFactory(
                authenticationServer
            )
        ).get(SplashScreenViewModel::class.java)
        splashScreenFragment.getTokenIsValid().observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.ERROR -> {
                    navigationController.navigate(action)
                }
                Resource.Status.OK -> {
                    startActivity(intent)
                }
                Resource.Status.LOADING -> return@observe
            }
        }


        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(
            SharedPreferencesInfo.preferencesName,
            Context.MODE_PRIVATE
        )
        val token: String? = sharedPref.getString(SharedPreferencesInfo.tokenName, null)

        if (token != null) {
            splashScreenFragment.authentificateUser(token)
        } else {
            navigationController.navigate(action)
        }
        return view
    }
}