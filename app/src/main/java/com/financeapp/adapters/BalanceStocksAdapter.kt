package com.financeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.financeapp.models.BalanceStock
import com.financeapp.utils.format
import com.financeapp.views.R
import java.text.FieldPosition
import java.text.SimpleDateFormat


class BalanceStocksAdapter() : RecyclerView.Adapter<BalanceStocksAdapter.BalanceStockHolder>() {

    private var balanceStocks: List<BalanceStock> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceStockHolder =
        BalanceStockHolder(LayoutInflater.from(parent.context).inflate(R.layout.balance_stock_item, parent, false))

    override fun getItemCount() = balanceStocks.size


    override fun onBindViewHolder(holder: BalanceStockHolder, position: Int) {
        holder.bind(balanceStocks[position])
    }

    fun refreshBalanceStocks(newBalanceStocks: List<BalanceStock>){
        balanceStocks = newBalanceStocks
        notifyDataSetChanged()
    }

    fun getStockId(viewHolderPosition: Int): Int {
        return balanceStocks[viewHolderPosition].id
    }

    inner class BalanceStockHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val name: TextView = itemView.findViewById(R.id.stockName)

        private val amount: TextView = itemView.findViewById(R.id.stockAmount)

        private val purchasePrice: TextView = itemView.findViewById(R.id.stockPurchasePrice)

        private val currentPrice: TextView = itemView.findViewById(R.id.stockCurrentPrice)

        private val lastUpdatedTime: TextView = itemView.findViewById(R.id.stockLastUpdatedTime)

        fun bind(stock: BalanceStock) {
            name.text = stock.name
            amount.text = "Amount ${stock.amount.format(2)}"
            purchasePrice.text = "Purchase price ${stock.averagePurchasePrice.format(2)}"
            currentPrice.text = "Current price ${stock.currentPrice.format(2)}"

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            lastUpdatedTime.text = formatter.format(stock.lastUpdatedTime)

        }
    }

}