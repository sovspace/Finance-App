package com.financeapp.webservice

import com.financeapp.models.Balance
import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.models.StockInfo
import com.financeapp.models.User
import com.financeapp.webservice.models.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


private object RestRoutes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign-up"
    const val GET_BALANCE = "balance"
    const val GET_BALANCE_HISTORY = "balance-history"
    const val BUY_STOCK = "buy-stock"
    const val SELL_STOCK = "sell-stock"
    const val REFILL = "refill"
    const val USER_INFO = "user-info"
    const val STOCK_INFO = "stock-info"
    const val CHANGE_AVATAR = "change-avatar"
    const val CHANGE_PASSWORD = "change-password"
    const val GET_BALANCE_HISTORY_CSV = "balance-history-csv"
    const val AUTHENTICATE_USER = "authenticate-user"
}


interface ActionsService {
    @GET(RestRoutes.GET_BALANCE)
    suspend fun getBalance(@Body lastUpdatedRequest: LastUpdatedRequest): Response<Balance>

    @GET(RestRoutes.GET_BALANCE_HISTORY)
    suspend fun getHistory(@Body lastUpdatedRequest: LastUpdatedRequest): Response<List<BalanceHistoryEntry>>

    @GET(RestRoutes.USER_INFO)
    suspend fun getUserInfo(@Body lastUpdatedRequest: LastUpdatedRequest): Response<User>

    @GET(RestRoutes.STOCK_INFO + "/{stock_symbol}")
    suspend fun getStockInfo(@Path("stock_symbol") stockSymbol: String): Response<StockInfo>

    @GET(RestRoutes.GET_BALANCE_HISTORY_CSV)
    suspend fun getBalanceHistoryCsv(): Response<String>

    @PUT(RestRoutes.BUY_STOCK)
    suspend fun buyStock(@Body buyRequest: BuyRequest): Response<String>

    @PUT(RestRoutes.SELL_STOCK)
    suspend fun sellStock(@Body sellRequest: SellRequest): Response<String>

    @PUT(RestRoutes.REFILL)
    suspend fun refill(@Body refillRequest: RefillRequest): Response<String>

    @PUT(RestRoutes.CHANGE_PASSWORD)
    suspend fun changePassword(@Body passwordRequest: PasswordRequest): Response<String>

    @Multipart
    @PUT(RestRoutes.CHANGE_AVATAR)
    suspend fun changeAvatar(@Part image: MultipartBody.Part): Response<String>
}

interface AuthenticationService {
    @POST(RestRoutes.SIGN_UP)
    suspend fun signUp(@Body signUpBody: SignUpRequest): Response<Unit>

    @POST(RestRoutes.LOGIN)
    suspend fun logIn(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @GET(RestRoutes.AUTHENTICATE_USER + "/{token}")
    suspend fun authenticateUser(@Path("token") token: String): Response<Unit>
}




