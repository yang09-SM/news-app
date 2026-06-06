package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageAdapter(
    private val messages: MutableList<ChatMessage>,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LEFT = 1
        private const val VIEW_TYPE_RIGHT = 2
    }

    inner class LeftMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvSenderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)

        fun bind(message: ChatMessage) {
            tvSenderName.text = message.senderName
            tvMessage.text = message.content
            tvTime.text = formatTime(message.time)
            if (message.senderAvatar.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(message.senderAvatar)
                    .placeholder(R.drawable.placeholder_news)
                    .error(R.drawable.placeholder_news)
                    .circleCrop()
                    .into(ivAvatar)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .circleCrop()
                    .into(ivAvatar)
            }
        }
    }

    inner class RightMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)

        fun bind(message: ChatMessage) {
            tvMessage.text = message.content
            tvTime.text = formatTime(message.time)
            if (message.senderAvatar.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(message.senderAvatar)
                    .placeholder(R.drawable.placeholder_news)
                    .error(R.drawable.placeholder_news)
                    .circleCrop()
                    .into(ivAvatar)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .circleCrop()
                    .into(ivAvatar)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserId) {
            VIEW_TYPE_RIGHT
        } else {
            VIEW_TYPE_LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message_right, parent, false)
            RightMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message_left, parent, false)
            LeftMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is RightMessageViewHolder) {
            holder.bind(message)
        } else if (holder is LeftMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun setMessages(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    private fun formatTime(time: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(time))
    }
}