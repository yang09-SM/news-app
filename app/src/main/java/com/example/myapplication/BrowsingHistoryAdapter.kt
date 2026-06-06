package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class BrowsingHistoryAdapter(
    private val historyList: MutableList<BrowsingHistoryItem>,
    private val onItemClickListener: (BrowsingHistoryItem) -> Unit,
    private val onDeleteClickListener: (BrowsingHistoryItem, Int) -> Unit
) : RecyclerView.Adapter<BrowsingHistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.history_title)
        val categoryTextView: TextView = itemView.findViewById(R.id.history_category)
        val timeTextView: TextView = itemView.findViewById(R.id.history_time)
        val historyImageView: ImageView = itemView.findViewById(R.id.history_image)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(historyItem: BrowsingHistoryItem, position: Int) {
            titleTextView.text = historyItem.title
            categoryTextView.text = historyItem.category
            timeTextView.text = formatTime(historyItem.browseTime)

            if (historyItem.pic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(historyItem.pic)
                    .apply(requestOptions)
                    .into(historyImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(historyImageView)
            }

            itemView.setOnClickListener {
                onItemClickListener(historyItem)
            }

            deleteButton.setOnClickListener {
                onDeleteClickListener(historyItem, position)
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
                    val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
                    sdf.format(Date(timestamp))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_browsing_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position], position)
    }

    override fun getItemCount(): Int = historyList.size

    fun removeItem(position: Int) {
        if (position >= 0 && position < historyList.size) {
            historyList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clearAll() {
        val size = historyList.size
        historyList.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun updateData(newList: List<BrowsingHistoryItem>) {
        historyList.clear()
        historyList.addAll(newList)
        notifyDataSetChanged()
    }
}
