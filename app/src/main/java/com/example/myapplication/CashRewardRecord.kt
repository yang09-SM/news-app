package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class CashRewardRecord(
    @SerializedName("id") val id: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("type") val type: RewardType,
    @SerializedName("description") val description: String,
    @SerializedName("time") val time: Long,
    @SerializedName("status") val status: RewardStatus
)

enum class RewardType {
    @SerializedName("read") READ,
    @SerializedName("share") SHARE,
    @SerializedName("invite") INVITE,
    @SerializedName("signin") SIGNIN,
    @SerializedName("withdraw") WITHDRAW
}

enum class RewardStatus {
    @SerializedName("pending") PENDING,
    @SerializedName("success") SUCCESS,
    @SerializedName("failed") FAILED
}
