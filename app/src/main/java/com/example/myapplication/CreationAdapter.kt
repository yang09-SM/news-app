package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CreationAdapter(
    private val creationList: MutableList<CreationItem>,
    private val onEditClickListener: (CreationItem, Int) -> Unit,
    private val onDeleteClickListener: (CreationItem, Int) -> Unit
) : RecyclerView.Adapter<CreationAdapter.CreationViewHolder>() {

    inner class CreationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.creationTitle)
        val contentTextView: TextView = itemView.findViewById(R.id.creationContent)
        val categoryTextView: TextView = itemView.findViewById(R.id.creationCategory)
        val timeTextView: TextView = itemView.findViewById(R.id.creationTime)
        val statusTextView: TextView = itemView.findViewById(R.id.creationStatus)
        val viewCountTextView: TextView = itemView.findViewById(R.id.creationViewCount)
        val likeCountTextView: TextView = itemView.findViewById(R.id.creationLikeCount)
        val commentCountTextView: TextView = itemView.findViewById(R.id.creationCommentCount)
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(creationItem: CreationItem, position: Int) {
            titleTextView.text = creationItem.title
            contentTextView.text = creationItem.content
            categoryTextView.text = creationItem.category
            timeTextView.text = formatTime(creationItem.createTime)
            
            viewCountTextView.text = creationItem.viewCount.toString()
            likeCountTextView.text = creationItem.likeCount.toString()
            commentCountTextView.text = creationItem.commentCount.toString()

            val (statusText, statusColor) = when (creationItem.status) {
                CreationStatus.DRAFT -> "草稿" to R.color.text_secondary
                CreationStatus.PUBLISHED -> "已发布" to R.color.quaternary_color
                CreationStatus.REVIEWING -> "审核中" to R.color.tertiary_color
                CreationStatus.REJECTED -> "已拒绝" to R.color.secondary_color
            }
            statusTextView.text = statusText
            statusTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, statusColor))

            editButton.setOnClickListener {
                onEditClickListener(creationItem, position)
            }

            deleteButton.setOnClickListener {
                onDeleteClickListener(creationItem, position)
            }
        }

        private fun formatTime(timestamp: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timestamp

            val minute = 60 * 1000
            val hour = 60 * minute
            val day = 24 * hour

            return when {
                diff < minute -> "刚刚"
                diff < hour -> "${diff / minute}分钟前"
                diff < day -> "${diff / hour}小时前"
                else -> {
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    sdf.format(Date(timestamp))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_creation, parent, false)
        return CreationViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreationViewHolder, position: Int) {
        holder.bind(creationList[position], position)
    }

    override fun getItemCount(): Int = creationList.size

    fun removeItem(position: Int) {
        if (position >= 0 && position < creationList.size) {
            creationList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(position: Int, item: CreationItem) {
        if (position >= 0 && position < creationList.size) {
            creationList[position] = item
            notifyItemChanged(position)
        }
    }

    fun addItem(item: CreationItem) {
        creationList.add(0, item)
        notifyItemInserted(0)
    }

    fun updateData(newList: List<CreationItem>) {
        creationList.clear()
        creationList.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearAll() {
        val size = creationList.size
        creationList.clear()
        notifyItemRangeRemoved(0, size)
    }
}
