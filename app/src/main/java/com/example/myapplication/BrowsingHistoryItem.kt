package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class BrowsingHistoryItem(
    @SerializedName("id") val id: String,
    @SerializedName("newsId") val newsId: String,
    @SerializedName("title") val title: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("category") val category: String,
    @SerializedName("url") val url: String,
    @SerializedName("browseTime") val browseTime: Long,
    @SerializedName("readDuration") val readDuration: Int
)
