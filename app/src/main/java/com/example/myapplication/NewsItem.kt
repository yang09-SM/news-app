package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("msg") val msg: String,
    @SerializedName("result") val result: NewsResult?
)

data class NewsResult(
    @SerializedName("list") val list: List<NewsItem>,
    @SerializedName("channel") val channel: String,
    @SerializedName("num") val num: Int
)

data class NewsItem(
    @SerializedName("title") val title: String,
    @SerializedName("time") val time: String,
    @SerializedName("src") val src: String,
    @SerializedName("category") val category: String,
    @SerializedName("url") val url: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("content") val content: String?
)
