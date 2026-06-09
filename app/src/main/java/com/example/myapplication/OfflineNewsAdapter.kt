package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class OfflineNewsAdapter(
    private val context: Context,
    private val newsList: MutableList<OfflineNewsItem>,
    private val onNewsClick: (OfflineNewsItem) -> Unit,
    private val onDeleteClick: (OfflineNewsItem) -> Unit
) : RecyclerView.Adapter<OfflineNewsAdapter.OfflineNewsViewHolder>() {

    inner class OfflineNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsPic: ImageView = view.findViewById(R.id.newsPic)
        val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        val newsCategory: TextView = view.findViewById(R.id.newsCategory)
        val downloadTime: TextView = view.findViewById(R.id.downloadTime)
        val deleteBtn: Button = view.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineNewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_offline_news, parent, false)
        return OfflineNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfflineNewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        holder.newsCategory.text = news.category
        
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        holder.downloadTime.text = "下载于 ${sdf.format(Date(news.downloadTime))}"
        
        Glide.with(context)
            .load(news.pic)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.newsPic)
        
        holder.itemView.setOnClickListener {
            onNewsClick(news)
        }
        
        holder.deleteBtn.setOnClickListener {
            onDeleteClick(news)
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newData: List<OfflineNewsItem>) {
        newsList.clear()
        newsList.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeNews(news: OfflineNewsItem) {
        val position = newsList.indexOf(news)
        if (position >= 0) {
            newsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
