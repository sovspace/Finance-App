package com.financeapp.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.financeapp.activities.MainActivity
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.ServerViewModelFactory
import com.financeapp.utils.SharedPreferencesInfo
import com.financeapp.viewmodels.LoginViewModel
import com.financeapp.views.databinding.LoginFragmentBinding
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.ServiceGenerator
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment, container, false
        )

        val authenticationServer = ServiceGenerator.createService(AuthenticationService::class.java)
        viewModel = ViewModelProvider(this,
            ServerViewModelFactory(
                authenticationServer
            )
        ).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpText.setOnClickListener {
            val action = LoginFragmentDirections.actionNavLoginFragmentToNavSignUpFragment()
            findNavController().navigate(action)
        }

        viewModel.getToken().observe(viewLifecycleOwner) {
            val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarLogin)

            when (it.status) {
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String).show()
                }
                Resource.Status.OK -> {
                    progressBar.hide()
                    val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(
                        SharedPreferencesInfo.preferencesName,
                        MODE_PRIVATE
                    )
                    sharedPref.edit().putString(SharedPreferencesInfo.tokenName, it.getData()).apply()

                    val goToMainActivity = Intent(
                        requireContext(),
                        MainActivity::class.java
                    )
                    startActivity(goToMainActivity)
                }
                Resource.Status.LOADING -> progressBar.show()
            }
        }
    }
}