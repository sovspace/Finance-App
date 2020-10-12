package com.financeapp.webservice.models


import com.google.gson.annotations.SerializedName

data class LastUpdatedRequest(
    @SerializedName("last_updated")
    var lastUpdated: Long?
)