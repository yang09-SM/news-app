package com.example.myapplication

data class FavoriteItem(
    val id: String = "",
    val newsId: String,
    val title: String,
    val pic: String,
    val category: String,
    val url: String,
    val favoriteTime: Long = System.currentTimeMillis()
)
