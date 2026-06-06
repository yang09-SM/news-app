package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ChatGroup(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("memberCount") val memberCount: Int,
    @SerializedName("lastMessage") val lastMessage: String?,
    @SerializedName("lastMessageTime") val lastMessageTime: Long?,
    @SerializedName("unreadCount") val unreadCount: Int
)
