package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(
    private val context: Context,
    private val comments: MutableList<Comment>,
    private val allComments: List<Comment>,
    private val onLikeClick: (Comment) -> Unit,
    private val onReplyClick: (Comment) -> Unit,
    private val onReportClick: (Comment) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userAvatar: ImageView = view.findViewById(R.id.userAvatar)
        val userName: TextView = view.findViewById(R.id.userName)
        val commentContent: TextView = view.findViewById(R.id.commentContent)
        val commentTime: TextView = view.findViewById(R.id.commentTime)
        val likeIcon: ImageView = view.findViewById(R.id.likeIcon)
        val likeCount: TextView = view.findViewById(R.id.likeCount)
        val likeLayout: LinearLayout = view.findViewById(R.id.likeLayout)
        val replyBtn: TextView = view.findViewById(R.id.replyBtn)
        val reportBtn: TextView = view.findViewById(R.id.reportBtn)
        val repliesContainer: LinearLayout = view.findViewById(R.id.repliesContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        
        holder.userName.text = comment.userName
        holder.commentContent.text = if (comment.replyToUserName != null) {
            "回复 ${comment.replyToUserName}: ${comment.content}"
        } else {
            comment.content
        }
        
        holder.commentTime.text = formatTime(comment.createTime)
        holder.likeCount.text = comment.likeCount.toString()
        
        if (comment.isLiked) {
            holder.likeIcon.setImageResource(android.R.drawable.star_big_on)
            holder.likeCount.setTextColor(context.resources.getColor(R.color.primary_color))
        } else {
            holder.likeIcon.setImageResource(android.R.drawable.star_big_off)
            holder.likeCount.setTextColor(context.resources.getColor(android.R.color.darker_gray))
        }
        
        Glide.with(context)
            .load(comment.userAvatar)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .circleCrop()
            .into(holder.userAvatar)
        
        holder.likeLayout.setOnClickListener {
            onLikeClick(comment)
        }
        
        holder.replyBtn.setOnClickListener {
            onReplyClick(comment)
        }
        
        holder.reportBtn.setOnClickListener {
            onReportClick(comment)
        }
        
        // 处理回复
        if (comment.parentId == null) {
            val replies = allComments.filter { it.parentId == comment.id }
            if (replies.isNotEmpty()) {
                holder.repliesContainer.visibility = View.VISIBLE
                holder.repliesContainer.removeAllViews()
                
                replies.forEach { reply ->
                    val replyView = LayoutInflater.from(context).inflate(R.layout.item_comment, holder.repliesContainer, false)
                    val replyHolder = CommentViewHolder(replyView)
                    
                    replyHolder.userName.text = reply.userName
                    replyHolder.commentContent.text = "回复 ${reply.replyToUserName}: ${reply.content}"
                    replyHolder.commentTime.text = formatTime(reply.createTime)
                    replyHolder.likeCount.text = reply.likeCount.toString()
                    
                    if (reply.isLiked) {
                        replyHolder.likeIcon.setImageResource(android.R.drawable.star_big_on)
                        replyHolder.likeCount.setTextColor(context.resources.getColor(R.color.primary_color))
                    } else {
                        replyHolder.likeIcon.setImageResource(android.R.drawable.star_big_off)
                        replyHolder.likeCount.setTextColor(context.resources.getColor(android.R.color.darker_gray))
                    }
                    
                    Glide.with(context)
                        .load(reply.userAvatar)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .circleCrop()
                        .into(replyHolder.userAvatar)
                    
                    replyHolder.likeLayout.setOnClickListener {
                        onLikeClick(reply)
                    }
                    
                    replyHolder.replyBtn.setOnClickListener {
                        onReplyClick(reply)
                    }
                    
                    replyHolder.reportBtn.setOnClickListener {
                        onReportClick(reply)
                    }
                    
                    replyHolder.repliesContainer.visibility = View.GONE
                    
                    holder.repliesContainer.addView(replyView)
                }
            } else {
                holder.repliesContainer.visibility = View.GONE
            }
        } else {
            holder.repliesContainer.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = comments.size

    fun updateComment(updatedComment: Comment) {
        val index = comments.indexOfFirst { it.id == updatedComment.id }
        if (index != -1) {
            comments[index] = updatedComment
            notifyItemChanged(index)
        }
    }

    fun addComment(comment: Comment) {
        comments.add(0, comment)
        notifyItemInserted(0)
    }

    private fun formatTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60000 -> "刚刚"
            diff < 3600000 -> "${diff / 60000}分钟前"
            diff < 86400000 -> "${diff / 3600000}小时前"
            diff < 604800000 -> "${diff / 86400000}天前"
            else -> {
                val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
                sdf.format(Date(timestamp))
            }
        }
    }
}
