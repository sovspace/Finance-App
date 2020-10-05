package com.financeapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.financeapp.viewmodels.HomeViewModel
import android.content.Context.MODE_PRIVATE
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.financeapp.adapters.BalanceStocksAdapter
import com.financeapp.utils.*
import com.financeapp.viewmodels.factories.TokenViewModelFactory
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.home_fragment.view.*


class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    val stockListAdapter: BalanceStocksAdapter = BalanceStocksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        val preferences = requireActivity().getSharedPreferences(
            SharedPreferencesInfo.preferencesName,
            MODE_PRIVATE
        )
        val token = preferences.getString(SharedPreferencesInfo.tokenName, "")

        viewModel =
            ViewModelProvider(requireActivity(),
                TokenViewModelFactory(token as String)
            ).get(
                HomeViewModel::class.java
            )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.stocksList)
        //recyclerView.addItemDecoration(MarginItemDecoration(10))
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = stockListAdapter

        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarHome)

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.sellStock(stockListAdapter.getStockId(viewHolder.adapterPosition))
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (!it.isHandled) {
                when (it.status) {
                    Resource.Status.OK -> {
                        progressBar.hide()
                        val balance = it.getData()
                        balance?.let {
                            progressBar.visibility = View.INVISIBLE

                            stockListAdapter.refreshBalanceStocks(it!!.stocks)

                            view.balanceText.text = "Balance " + it.amount.format(2)
                            view.currencyText.text = "Currency " + it.currency.toString()
                        }
                    }
                    Resource.Status.ERROR -> {
                        progressBar.hide()
                        Toasty.error(requireContext(), it.getMessage() as String).show()
                    }
                    Resource.Status.LOADING -> progressBar.show()
                }
            }
        }

        viewModel.requestBalance()

    }
}