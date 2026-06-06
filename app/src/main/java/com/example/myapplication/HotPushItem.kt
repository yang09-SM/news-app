package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class HotPushItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("pushTime") val pushTime: Long,
    @SerializedName("isTop") val isTop: Boolean,
    @SerializedName("views") val views: Int = 0,
    @SerializedName("likes") val likes: Int = 0,
    @SerializedName("comments") val comments: Int = 0,
    @SerializedName("newsUrl") val newsUrl: String = ""
)
