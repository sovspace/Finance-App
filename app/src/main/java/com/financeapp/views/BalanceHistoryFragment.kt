package com.financeapp.views

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.financeapp.adapters.BalanceHistoryAdapter
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.TokenViewModelFactory
import com.financeapp.utils.SharedPreferencesInfo
import com.financeapp.viewmodels.BalanceHistoryViewModel
import android.content.Intent
import es.dmoral.toasty.Toasty


class BalanceHistoryFragment : Fragment() {
    private lateinit var viewModel: BalanceHistoryViewModel

    private var balanceHistoryAdapter: BalanceHistoryAdapter = BalanceHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.balance_history_fragment, container, false)

        val preferences = requireActivity().getSharedPreferences(
            SharedPreferencesInfo.preferencesName,
            Context.MODE_PRIVATE
        )
        val token = preferences.getString(SharedPreferencesInfo.tokenName, "")

        viewModel = ViewModelProvider(
            requireActivity(),
            TokenViewModelFactory(
                token as String,
                context
            )
        ).get(BalanceHistoryViewModel::class.java)

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.stocksList).apply {
            //addItemDecoration(MarginItemDecoration(15))
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = balanceHistoryAdapter
        }

        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarHistory)


        viewModel.balanceHistory.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.OK -> {
                    progressBar.hide()
                    val balance = it.getData()
                    balance?.let {
                        progressBar.visibility = View.INVISIBLE
                        balanceHistoryAdapter.refreshBalanceHistory(it)
                    }
                }
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String).show()
                }
                Resource.Status.LOADING -> progressBar.show()
            }
        }

        viewModel.getBalanceHistory()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.balance_history_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.emailButton) {
            viewModel.getBalanceHistoryCsv()

            val progressBar: ContentLoadingProgressBar =
                requireView().findViewById(R.id.progressBarHistory)

            viewModel.csvUri.observe(viewLifecycleOwner) {
                if (it.status == Resource.Status.LOADING) {
                    progressBar.show()
                }
                if (it.status == Resource.Status.OK) {
                    progressBar.hide()
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "message/rfc822"
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    intent.putExtra(Intent.EXTRA_STREAM, it.getData())

                    try {
                        startActivity(Intent.createChooser(intent, "Send mail..."))
                    } catch (ex: android.content.ActivityNotFoundException) {
                        Toasty.error(requireContext(), "There are no email clients installed.")
                            .show()
                    }
                }
                if (it.status == Resource.Status.ERROR) {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String).show()
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }
}