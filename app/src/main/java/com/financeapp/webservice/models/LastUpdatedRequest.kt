package com.financeapp.webservice.models


import com.google.gson.annotations.SerializedName
import java.util.*

data class LastUpdatedRequest(
    @SerializedName("last_updated")
    var lastUpdated: Date?
)