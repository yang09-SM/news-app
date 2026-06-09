
package com.example.myapplication

import java.io.Serializable

data class Comment(
    val id: String,
    val newsId: String,
    val userId: String,
    val userName: String,
    val userAvatar: String,
    val content: String,
    val parentId: String? = null, // null表示一级评论，否则为二级评论
    val replyToUserName: String? = null, // 回复的用户名
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val createTime: Long = System.currentTimeMillis(),
    val replyCount: Int = 0
) : Serializable
