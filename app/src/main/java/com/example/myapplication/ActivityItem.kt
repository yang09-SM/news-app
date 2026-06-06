package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ActivityItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("startTime") val startTime: Long,
    @SerializedName("endTime") val endTime: Long,
    @SerializedName("location") val location: String,
    @SerializedName("participantCount") val participantCount: Int,
    @SerializedName("status") val status: ActivityStatus,
    @SerializedName("isRegistered") val isRegistered: Boolean = false
)

enum class ActivityStatus {
    @SerializedName("upcoming") UPCOMING,
    @SerializedName("ongoing") ONGOING,
    @SerializedName("ended") ENDED
}
