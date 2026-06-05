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
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 1
        private const val VIEW_TYPE_2 = 2
        private const val VIEW_TYPE_3 = 3
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        val timeTextView: TextView = itemView.findViewById(R.id.news_time)
        val srcTextView: TextView = itemView.findViewById(R.id.news_src)
        val newsImageView: ImageView = itemView.findViewById(R.id.news_image)
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
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 3) {
            0 -> VIEW_TYPE_1
            1 -> VIEW_TYPE_2
            else -> VIEW_TYPE_3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutRes = when (viewType) {
            VIEW_TYPE_2 -> R.layout.news_item_type2
            VIEW_TYPE_3 -> R.layout.news_item_type3
            else -> R.layout.news_item
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
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
}
