package com.financeapp.viewmodels

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.repositories.BalanceHistoryRepository
import com.financeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class BalanceHistoryViewModel(token: String, val context: Context) : ViewModel() {

    private val authenticatedRepository: BalanceHistoryRepository = BalanceHistoryRepository(token)
    val balanceHistory: MutableLiveData<Resource<List<BalanceHistoryEntry>>> = MutableLiveData()
    val csvUri: MutableLiveData<Resource<Uri>> = MutableLiveData()

    private val tempFileName = "email/history.csv"
    private val tempDirName = "email"

    fun getBalanceHistory() {
        this.viewModelScope.launch(Dispatchers.IO) {
            balanceHistory.postValue(Resource.loading())
            balanceHistory.postValue(authenticatedRepository.getBalanceHistory())
        }
    }


    fun getBalanceHistoryCsv() {
        this.viewModelScope.launch(Dispatchers.IO) {
            csvUri.value = Resource.loading()
            val resourceCsv = authenticatedRepository.getBalanceHistoryCsv()

            if (resourceCsv.status == Resource.Status.OK) {
                val dir = File(context.cacheDir, tempDirName)
                dir.mkdirs()

                val file = File(context.cacheDir, tempFileName)
                file.writeBytes(resourceCsv.getData()!!.toByteArray())
                csvUri.value = Resource.success(
                    FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file
                    )
                )
            } else {
                csvUri.value = Resource.error(resourceCsv.getMessage())
            }
        }
    }
}