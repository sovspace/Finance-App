package com.financeapp.dataentries

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.financeapp.models.StockHistoryEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StockHistoryDataEntry(data: String, price: Number): ValueDataEntry(data, price){
    companion object{
        fun history(entries: Array<StockHistoryEntry>) : ArrayList<DataEntry>{
            val dataEntries = ArrayList<DataEntry>()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            for (entry in entries){

                dataEntries.add(
                    StockHistoryDataEntry(
                        formatter.format(entry.date),
                        entry.price
                    )
                )
            }
            return dataEntries
        }
    }
}