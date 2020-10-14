package com.financeapp.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.TokenViewModelFactory
import com.financeapp.utils.Constants
import com.financeapp.viewmodels.BuyStocksViewModel
import com.financeapp.views.databinding.BuyStocksFragmentBinding
import es.dmoral.toasty.Toasty


class BuyStocksFragment : Fragment(){

    private lateinit var viewModel: BuyStocksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val preferences = requireActivity().getSharedPreferences(
            Constants.preferencesName,
            Context.MODE_PRIVATE
        )
        val token = preferences.getString(Constants.tokenName, "")

        viewModel = ViewModelProvider(requireActivity(),
            TokenViewModelFactory(token as String)
        ).get(
            BuyStocksViewModel::class.java)

        val binding : BuyStocksFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.buy_stocks_fragment, container, false
        )

        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarBuy)
        viewModel.getResponse().observe(viewLifecycleOwner){
            if(!it.isHandled) {
                when (it.status) {
                    Resource.Status.LOADING -> progressBar.visibility = ProgressBar.VISIBLE
                    Resource.Status.ERROR -> {
                        progressBar.hide()
                        Toasty.error(requireContext(), it.getMessage() as String).show()
                    }
                    Resource.Status.OK -> {
                        progressBar.hide()
                        Toasty.success(requireContext(), it.getData() as String).show()
                    }
                }
            }
        }
    }
}