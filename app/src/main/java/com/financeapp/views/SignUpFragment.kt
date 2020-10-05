package com.financeapp.views

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
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.ServerViewModelFactory
import com.financeapp.viewmodels.SignUpViewModel
import com.financeapp.views.databinding.SignUpFragmentBinding
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.ServiceGenerator
import es.dmoral.toasty.Toasty


class SignUpFragment: Fragment() {

    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding: SignUpFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.sign_up_fragment, container, false
        )

        val authenticationServer = ServiceGenerator.createService(AuthenticationService::class.java)
        signUpViewModel = ViewModelProvider(this,
            ServerViewModelFactory(
                authenticationServer
            )
        ).get(SignUpViewModel::class.java)
        binding.viewmodel = signUpViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarSignUp)

        signUpViewModel.getIsSignedUp().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.OK -> {
                    progressBar.hide()
                    findNavController().popBackStack()
                }
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String).show()
                }
                Resource.Status.LOADING -> progressBar.show()
            }
        }
    }
}