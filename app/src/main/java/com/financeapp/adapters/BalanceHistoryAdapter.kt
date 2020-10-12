package com.financeapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.utils.format
import com.financeapp.views.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.*
import kotlin.collections.ArrayList

class BalanceHistoryAdapter() : RecyclerView.Adapter<BalanceHistoryAdapter.BalanceHistoryEntryHolder>() {

    private var historyEntries: List<BalanceHistoryEntry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceHistoryEntryHolder =
        BalanceHistoryEntryHolder(LayoutInflater.from(parent.context).inflate(R.layout.balance_history_item, parent, false))

    override fun getItemCount() = historyEntries.size


    override fun onBindViewHolder(holder: BalanceHistoryEntryHolder, position: Int) {
        holder.bind(historyEntries[position])
    }

    fun refreshBalanceHistory(newHistoryEntries: List<BalanceHistoryEntry>){
        historyEntries = newHistoryEntries
        notifyDataSetChanged()
    }

    inner class BalanceHistoryEntryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val type: TextView = itemView.findViewById(R.id.type)

        private val amount: TextView = itemView.findViewById(R.id.amount)

        private val totalCost: TextView = itemView.findViewById(R.id.totalCost)

        private val stockName: TextView = itemView.findViewById(R.id.stockName)

        private val operationTime: TextView = itemView.findViewById(R.id.operationTime)

        @SuppressLint("SetTextI18n")
        fun bind(entry: BalanceHistoryEntry) {
            entry.also {
                type.text = it.type.capitalize()
                totalCost.text = "Total cost ${it.totalCost.format(2)}"

                if (it.amount != null) {
                    amount.text = "Amount ${it.amount}"
                } else {
                    amount.visibility = TextView.INVISIBLE
                }

                if (it.stockName != null) {
                    stockName.text = it.stockName
                } else {
                    stockName.visibility = TextView.INVISIBLE
                }

                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getDefault())
                formatter.timeZone = TimeZone.getDefault()
                operationTime.text = formatter.format(it.operationTime)
            }


        }
    }
}

