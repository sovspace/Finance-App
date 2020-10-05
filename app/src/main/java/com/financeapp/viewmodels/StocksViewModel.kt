package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.AnyChart
import com.anychart.charts.Cartesian
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.ScaleStackMode
import com.anychart.enums.TooltipPositionMode
import com.financeapp.repositories.AuthenticatedRepository
import com.financeapp.dataentries.StockHistoryDataEntry
import com.financeapp.models.StockInfo
import com.financeapp.dataentries.StockPredictionDataEntry
import com.financeapp.repositories.StockSearchRepository
import com.financeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StocksViewModel(token: String) : ViewModel() {

    private val authenticatedRepository = StockSearchRepository(token)

    var stockSymbol = String()
    private val stockInfo: MutableLiveData<Resource<StockInfo>> = MutableLiveData()
    fun getStockInfo(): LiveData<Resource<StockInfo>> = stockInfo

    fun requestStockInfo() {
        this.viewModelScope.launch(Dispatchers.IO) {
            stockInfo.postValue(Resource.loading())
            stockInfo.postValue(authenticatedRepository.getStockInfo(stockSymbol))
        }
    }

    fun plotGraph(): Cartesian {

        val cartesian = AnyChart.cartesian()
        cartesian.animation(true)

        cartesian.title("${stockInfo.value?.getData()?.stockSymbol}")


        cartesian.yScale().stackMode(ScaleStackMode.VALUE)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)


        cartesian.yAxis(0).title("Price").labels().padding(1, 1, 1, 1)
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val dataHistory = StockHistoryDataEntry.history(stockInfo.value!!.getData()!!.history)
        val dataPredictions =
            StockPredictionDataEntry.Predictions(stockInfo.value!!.getData()!!.predictions)

        val setHistory = Set.instantiate()
        setHistory.data(dataHistory)

        val historyMapping = setHistory.mapAs("{ x: 'data', value: 'price' }")
        val historyLine = cartesian.line(historyMapping)
        historyLine.name("History")
        historyLine.hovered().markers().enabled(true)
        historyLine.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        historyLine.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)


        val setPrediction = Set.instantiate()
        setPrediction.data(dataPredictions)

        val predictionPriceMapping = setPrediction.mapAs("{ x: 'data', value: 'prediction' }")
        val predictionPriceLine = cartesian.line(predictionPriceMapping )
        predictionPriceLine .name("Prediction")
        predictionPriceLine .hovered().markers().enabled(true)
        predictionPriceLine .hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        predictionPriceLine .tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)


        val predictionUpperLowerPriceMapping = setPrediction.mapAs("{x: 'data', high: 'upperPrice', low: 'lowerPrice'}")
        val predictionUpperLowerPrice = cartesian.rangeColumn(predictionUpperLowerPriceMapping)
        predictionUpperLowerPrice.name("Bounds")
        predictionPriceLine.color("Red")

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(5.0, 5.0, 5.0, 5.0)

        return cartesian
    }
}


