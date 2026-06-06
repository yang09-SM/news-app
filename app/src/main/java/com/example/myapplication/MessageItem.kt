package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class MessageItem(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: MessageType,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("time") val time: Long,
    @SerializedName("isRead") val isRead: Boolean
)

enum class MessageType {
    @SerializedName("system") SYSTEM,
    @SerializedName("like") LIKE,
    @SerializedName("comment") COMMENT,
    @SerializedName("follow") FOLLOW
}
