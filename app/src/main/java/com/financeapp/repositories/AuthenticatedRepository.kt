package com.financeapp.repositories
//
//import com.financeapp.models.Balance
//import com.financeapp.models.BalanceHistoryEntry
//import com.financeapp.models.StockInfo
//import com.financeapp.models.User
//import com.financeapp.utils.Resource
//import com.financeapp.webservice.ServiceGenerator
//import com.financeapp.webservice.ActionsService
//import com.financeapp.webservice.models.*
//import java.net.ConnectException
//import java.net.SocketTimeoutException
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import java.io.File
//
//
//class AuthenticatedRepository(token: String){
//
//    private val authenticateService: ActionsService = ServiceGenerator.createService(ActionsService::class.java, token)
//
//
//    suspend fun getBalance() : Resource<Balance>{
//
//        try {
//            val response = authenticateService.getBalance()
//
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//    suspend fun getBalanceHistory() : Resource<List<BalanceHistoryEntry>>{
//        try {
//            val response = authenticateService.getHistory()
//
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//
//    }
//
//    suspend fun getUserInfo() : Resource<User>{
//
//        try {
//            val response = authenticateService.getUserInfo()
//
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401 || response.code() == 404){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//
//    }
//
//    suspend fun getStockInfo(stockSymbol: String) : Resource<StockInfo>{
//
//        try {
//
//            val response = authenticateService.getStockInfo(stockSymbol)
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401 || response.code() == 404){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//
//    }
//
//
//    suspend fun buyStock(amount: Double, stockSymbol: String): Resource<String>{
//        try {
//            val request = BuyRequest(stockSymbol, amount)
//            val response = authenticateService.buyStock(request)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if (response.code() == 401)
//                return Resource.error(response.message())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//    suspend fun sellStock(stockId: Int): Resource<String>{
//        try {
//            val request = SellRequest(stockId)
//            val response = authenticateService.sellStock(request)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if (response.code() == 401)
//                return Resource.error(response.message())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//
//    suspend fun refillStock(amount: Double): Resource<String>{
//
//        try {
//            val request = RefillRequest(amount)
//            val response = authenticateService.refill(request)
//
//
//            if (response.code() == 401)
//                return Resource.error(response.message())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//            if (response.code() == 404)
//            return Resource.error(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//
//    suspend fun changePassword(currentPassword: String, newPassword: String): Resource<String>{
//        try {
//            val request = PasswordRequest(currentPassword, newPassword)
//            val response = authenticateService.changePassword(request)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//    suspend fun changeAvatar(avatarUri: String): Resource<String>{
//        try {
//
//
//            val file = File(avatarUri)
//            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
//            val response = authenticateService.changeAvatar(body)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//    suspend fun getBalanceHistoryCsv(): Resource<String>{
//        try {
//
//            val response = authenticateService.getBalanceHistoryCsv()
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
//
//
//
//}