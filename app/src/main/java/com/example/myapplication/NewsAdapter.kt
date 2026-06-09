package com.example.myapplication

import android.graphics.drawable.Drawable
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

class NewsAdapter(
    private val newsList: MutableList<NewsItem>,
    private val onItemClickListener: (NewsItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onItemLongClickListener: ((Int) -> Unit)? = null

    fun setOnItemLongClickListener(listener: (Int) -> Unit) {
        onItemLongClickListener = listener
    }

    companion object {
        private const val VIEW_TYPE_TEXT_1 = 1
        private const val VIEW_TYPE_TEXT_2 = 2
        private const val VIEW_TYPE_TEXT_3 = 3
        private const val VIEW_TYPE_VIDEO = 4
        private const val VIEW_TYPE_AUDIO = 5
    }

    inner class TextNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        val timeTextView: TextView = itemView.findViewById(R.id.news_time)
        val srcTextView: TextView = itemView.findViewById(R.id.news_src)
        val newsImageView: ImageView? = itemView.findViewById(R.id.news_image)
        val viewsTextView: TextView? = itemView.findViewById(R.id.news_views)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            timeTextView.text = newsItem.time
            srcTextView.text = newsItem.src

            newsImageView?.let {
                if (newsItem.pic.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(newsItem.pic)
                        .apply(requestOptions)
                        .into(it)
                } else {
                    Glide.with(itemView.context)
                        .load(R.drawable.placeholder_news)
                        .apply(requestOptions)
                        .into(it)
                }
            }

            itemView.setOnClickListener {
                onItemClickListener(newsItem)
            }

            itemView.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it(adapterPosition)
                    true
                } ?: false
            }
        }
    }

    inner class VideoNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        val timeTextView: TextView = itemView.findViewById(R.id.news_time)
        val srcTextView: TextView = itemView.findViewById(R.id.news_src)
        val newsImageView: ImageView = itemView.findViewById(R.id.news_image)
        val durationTextView: TextView = itemView.findViewById(R.id.video_duration)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            timeTextView.text = newsItem.time
            srcTextView.text = newsItem.src
            durationTextView.text = newsItem.duration ?: "00:00"

            if (newsItem.pic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(newsItem.pic)
                    .apply(requestOptions)
                    .into(newsImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(newsImageView)
            }

            itemView.setOnClickListener {
                onItemClickListener(newsItem)
            }

            itemView.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it(adapterPosition)
                    true
                } ?: false
            }
        }
    }

    inner class AudioNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        val timeTextView: TextView = itemView.findViewById(R.id.news_time)
        val srcTextView: TextView = itemView.findViewById(R.id.news_src)
        val durationTextView: TextView = itemView.findViewById(R.id.audio_duration)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            timeTextView.text = newsItem.time
            srcTextView.text = newsItem.src
            durationTextView.text = newsItem.duration ?: "00:00"

            itemView.setOnClickListener {
                onItemClickListener(newsItem)
            }

            itemView.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it(adapterPosition)
                    true
                } ?: false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val newsItem = newsList[position]
        return when (newsItem.type) {
            "video" -> VIEW_TYPE_VIDEO
            "audio" -> VIEW_TYPE_AUDIO
            else -> when (position % 3) {
                0 -> VIEW_TYPE_TEXT_1
                1 -> VIEW_TYPE_TEXT_2
                else -> VIEW_TYPE_TEXT_3
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutRes = when (viewType) {
            VIEW_TYPE_VIDEO -> R.layout.news_item_video
            VIEW_TYPE_AUDIO -> R.layout.news_item_audio
            VIEW_TYPE_TEXT_2 -> R.layout.news_item_type2
            VIEW_TYPE_TEXT_3 -> R.layout.news_item_type3
            else -> R.layout.news_item
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)
        
        return when (viewType) {
            VIEW_TYPE_VIDEO -> VideoNewsViewHolder(view)
            VIEW_TYPE_AUDIO -> AudioNewsViewHolder(view)
            else -> TextNewsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItem = newsList[position]
        when (holder) {
            is TextNewsViewHolder -> holder.bind(newsItem)
            is VideoNewsViewHolder -> holder.bind(newsItem)
            is AudioNewsViewHolder -> holder.bind(newsItem)
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun addNews(news: List<NewsItem>) {
        val startPosition = newsList.size
        newsList.addAll(news)
        notifyItemRangeInserted(startPosition, news.size)
    }

    fun clearNews() {
        newsList.clear()
        notifyDataSetChanged()
    }

    fun updateData(news: List<NewsItem>) {
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
    }
}
