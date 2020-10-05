package com.financeapp.dataentries

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.financeapp.models.StockPredictionEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StockPredictionDataEntry(data: String,
                               prediction: Number,
                               upperPrice: Number,
                               lowerPrice: Number): ValueDataEntry(data, prediction){

    init {
        setValue("upperPrice", upperPrice)
        setValue("lowerPrice", lowerPrice)
    }
    companion object{
        fun Predictions(entries: Array<StockPredictionEntry>): ArrayList<DataEntry>{
            val dataEntries = ArrayList<DataEntry>()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            for (entry in entries){
                dataEntries.add(
                    StockPredictionDataEntry(
                        formatter.format(entry.date),
                        entry.predictedPrice,
                        entry.upperPrice,
                        entry.lowerPrice
                    )
                )
            }
            return dataEntries
        }
    }
}