package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("id") val id: String,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("senderId") val senderId: String,
    @SerializedName("senderName") val senderName: String,
    @SerializedName("senderAvatar") val senderAvatar: String,
    @SerializedName("content") val content: String,
    @SerializedName("type") val type: ChatMessageType,
    @SerializedName("time") val time: Long,
    @SerializedName("isRead") val isRead: Boolean
)

enum class ChatMessageType {
    @SerializedName("text") TEXT,
    @SerializedName("image") IMAGE,
    @SerializedName("voice") VOICE,
    @SerializedName("video") VIDEO
}
