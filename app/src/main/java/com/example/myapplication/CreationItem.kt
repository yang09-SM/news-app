package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class CreationItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("category") val category: String,
    @SerializedName("createTime") val createTime: Long,
    @SerializedName("viewCount") val viewCount: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("status") val status: CreationStatus
)

enum class CreationStatus {
    @SerializedName("draft") DRAFT,
    @SerializedName("published") PUBLISHED,
    @SerializedName("reviewing") REVIEWING,
    @SerializedName("rejected") REJECTED
}
