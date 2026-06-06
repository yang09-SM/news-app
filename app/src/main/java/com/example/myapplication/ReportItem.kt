package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ReportItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("location") val location: String,
    @SerializedName("category") val category: String,
    @SerializedName("createTime") val createTime: Long,
    @SerializedName("status") val status: ReportStatus,
    @SerializedName("points") val points: Int?
)

enum class ReportStatus {
    @SerializedName("submitted") SUBMITTED,
    @SerializedName("reviewing") REVIEWING,
    @SerializedName("accepted") ACCEPTED,
    @SerializedName("rejected") REJECTED
}
