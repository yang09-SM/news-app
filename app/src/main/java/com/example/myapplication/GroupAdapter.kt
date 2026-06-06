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

class GroupAdapter(
    private val groups: MutableList<ChatGroup>,
    private val onItemClickListener: (ChatGroup) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvGroupName: TextView = itemView.findViewById(R.id.tvGroupName)
        val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvUnreadCount: TextView = itemView.findViewById(R.id.tvUnreadCount)

        fun bind(group: ChatGroup) {
            tvGroupName.text = group.name
            if (group.avatar.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(group.avatar)
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
            tvLastMessage.text = group.lastMessage ?: "暂无消息"
            tvTime.text = if (group.lastMessageTime != null) formatTime(group.lastMessageTime) else ""
            
            if (group.unreadCount > 0) {
                tvUnreadCount.visibility = View.VISIBLE
                tvUnreadCount.text = if (group.unreadCount > 99) "99+" else group.unreadCount.toString()
            } else {
                tvUnreadCount.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClickListener(group)
            }
        }

        private fun formatTime(time: Long): String {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            return sdf.format(Date(time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }

    override fun getItemCount(): Int = groups.size

    fun setGroups(newGroups: List<ChatGroup>) {
        groups.clear()
        groups.addAll(newGroups)
        notifyDataSetChanged()
    }
}