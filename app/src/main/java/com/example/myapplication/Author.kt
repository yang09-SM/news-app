package com.example.myapplication

data class Author(
    val id: String,
    val name: String,
    val avatar: String,
    val bio: String,
    val followerCount: Int,
    val articleCount: Int,
    val isFollowed: Boolean = false
)
