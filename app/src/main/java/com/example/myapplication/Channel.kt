package com.example.myapplication

data class Channel(
    val id: String,
    val name: String,
    val icon: String,
    val description: String,
    val newsCount: Int,
    val isSubscribed: Boolean = false
)
