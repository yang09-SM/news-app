package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ExchangeRecord(
    @SerializedName("id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPic") val productPic: String,
    @SerializedName("points") val points: Int,
    @SerializedName("exchangeTime") val exchangeTime: Long,
    @SerializedName("status") val status: ExchangeStatus
)

enum class ExchangeStatus {
    @SerializedName("pending") PENDING,
    @SerializedName("processing") PROCESSING,
    @SerializedName("completed") COMPLETED,
    @SerializedName("cancelled") CANCELLED
}
