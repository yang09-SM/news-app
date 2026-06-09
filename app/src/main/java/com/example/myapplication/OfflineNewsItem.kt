package com.example.myapplication

data class OfflineNewsItem(
    val id: String,
    val title: String,
    val pic: String,
    val category: String,
    val url: String,
    val content: String? = null, // 存储 HTML 内容
    val downloadTime: Long = System.currentTimeMillis()
)
