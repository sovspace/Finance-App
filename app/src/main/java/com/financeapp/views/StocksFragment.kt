package com.financeapp.views

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.anychart.AnyChartView
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.TokenViewModelFactory
import com.financeapp.utils.Constants
import com.financeapp.viewmodels.StocksViewModel
import com.financeapp.views.databinding.StocksFragmentBinding
import es.dmoral.toasty.Toasty

class StocksFragment : Fragment() {

    lateinit var viewModel: StocksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)


        val binding: StocksFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.stocks_fragment, container, false
        )
        val preferences = requireActivity().getSharedPreferences(
            Constants.preferencesName,
            Context.MODE_PRIVATE
        )
        val token = preferences.getString(Constants.tokenName, "")

        viewModel = ViewModelProvider(requireActivity(),
            TokenViewModelFactory(token as String)
        ).get(
                StocksViewModel::class.java
            )

        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarStocks)

        viewModel.getStockInfo().observe(viewLifecycleOwner){
            when(it.status) {
                Resource.Status.OK -> {
                    val chart: AnyChartView = view.findViewById(R.id.stockChart)
                    chart.visibility = View.VISIBLE
                    chart.setChart(viewModel.plotGraph())
                }
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String).show()
                }
                Resource.Status.LOADING -> {
                    progressBar.show()

                }
            }
        }
    }


}