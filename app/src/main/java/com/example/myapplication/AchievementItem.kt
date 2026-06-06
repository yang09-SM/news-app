package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class AchievementItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("points") val points: Int,
    @SerializedName("isUnlocked") val isUnlocked: Boolean,
    @SerializedName("unlockTime") val unlockTime: Long?,
    @SerializedName("progress") val progress: Int,
    @SerializedName("target") val target: Int
)
