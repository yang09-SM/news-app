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

class HotPushAdapter(
    private val hotPushList: MutableList<HotPushItem>,
    private val onItemClickListener: (HotPushItem) -> Unit
) : RecyclerView.Adapter<HotPushAdapter.HotPushViewHolder>() {

    inner class HotPushViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.hot_push_title)
        val contentTextView: TextView = itemView.findViewById(R.id.hot_push_content)
        val timeTextView: TextView = itemView.findViewById(R.id.hot_push_time)
        val viewsTextView: TextView = itemView.findViewById(R.id.hot_push_views)
        val likesTextView: TextView = itemView.findViewById(R.id.hot_push_likes)
        val commentsTextView: TextView = itemView.findViewById(R.id.hot_push_comments)
        val hotPushImageView: ImageView = itemView.findViewById(R.id.hot_push_image)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(hotPushItem: HotPushItem) {
            titleTextView.text = hotPushItem.title
            contentTextView.text = hotPushItem.content
            timeTextView.text = formatTime(hotPushItem.pushTime)
            viewsTextView.text = "👁 ${formatNumber(hotPushItem.views)}"
            likesTextView.text = "❤️ ${formatNumber(hotPushItem.likes)}"
            commentsTextView.text = "💬 ${formatNumber(hotPushItem.comments)}"

            if (hotPushItem.pic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(hotPushItem.pic)
                    .apply(requestOptions)
                    .into(hotPushImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(hotPushImageView)
            }

            itemView.setOnClickListener {
                onItemClickListener(hotPushItem)
            }
        }

        private fun formatTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }

        private fun formatNumber(num: Int): String {
            return when {
                num >= 10000 -> "${String.format("%.1f", num / 10000.0)}万"
                num >= 1000 -> "${String.format("%.1f", num / 1000.0)}k"
                else -> num.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotPushViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hot_push, parent, false)
        return HotPushViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotPushViewHolder, position: Int) {
        holder.bind(hotPushList[position])
    }

    override fun getItemCount(): Int = hotPushList.size

    fun updateData(newList: List<HotPushItem>) {
        hotPushList.clear()
        hotPushList.addAll(newList)
        notifyDataSetChanged()
    }
}
