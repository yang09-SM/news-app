package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val messages: MutableList<MessageItem>,
    private val onItemClickListener: (MessageItem) -> Unit,
    private val onMoreClickListener: (MessageItem, View) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val indicatorContainer: LinearLayout = itemView.findViewById(R.id.indicatorContainer)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val ivMore: ImageView = itemView.findViewById(R.id.ivMore)

        fun bind(message: MessageItem) {
            tvTitle.text = message.title
            tvContent.text = message.content
            tvTime.text = formatTime(message.time)
            
            indicatorContainer.visibility = if (message.isRead) View.GONE else View.VISIBLE

            itemView.setOnClickListener {
                onItemClickListener(message)
            }

            ivMore.setOnClickListener {
                onMoreClickListener(message, ivMore)
            }
        }

        private fun formatTime(time: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            return sdf.format(Date(time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    fun removeMessage(id: String) {
        val index = messages.indexOfFirst { it.id == id }
        if (index != -1) {
            messages.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateMessage(updatedMessage: MessageItem) {
        val index = messages.indexOfFirst { it.id == updatedMessage.id }
        if (index != -1) {
            messages[index] = updatedMessage
            notifyItemChanged(index)
        }
    }

    fun setMessages(newMessages: List<MessageItem>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }
}
