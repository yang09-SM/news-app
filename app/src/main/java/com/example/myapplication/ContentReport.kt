package com.example.myapplication

data class ContentReport(
    val id: String = "",
    val newsId: String = "",
    val newsTitle: String = "",
    val commentId: String? = null,
    val reason: String = "",
    val description: String = "",
    val reporterId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "pending"
)
