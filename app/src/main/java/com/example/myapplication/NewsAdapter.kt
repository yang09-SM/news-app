package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(
    private val newsList: MutableList<NewsItem>,
    private val onItemClickListener: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.news_title)
        val timeTextView: TextView = itemView.findViewById(R.id.news_time)
        val srcTextView: TextView = itemView.findViewById(R.id.news_src)
        val newsImageView: ImageView = itemView.findViewById(R.id.news_image)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            timeTextView.text = newsItem.time
            srcTextView.text = newsItem.src

            if (newsItem.pic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(newsItem.pic)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(newsImageView)
            } else {
                newsImageView.setImageResource(R.drawable.ic_launcher_foreground)
            }

            itemView.setOnClickListener {
                onItemClickListener(newsItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
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
