package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TopicAdapter(
    private var topics: List<Topic>,
    private val onTopicClick: (Topic) -> Unit
) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    fun updateData(newTopics: List<Topic>) {
        topics = newTopics
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topics[position])
    }

    override fun getItemCount(): Int = topics.size

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicCover: ImageView = itemView.findViewById(R.id.topicCover)
        private val topicName: TextView = itemView.findViewById(R.id.topicName)
        private val topicDescription: TextView = itemView.findViewById(R.id.topicDescription)
        private val topicNewsCount: TextView = itemView.findViewById(R.id.topicNewsCount)
        private val topicDiscussionCount: TextView = itemView.findViewById(R.id.topicDiscussionCount)

        fun bind(topic: Topic) {
            Glide.with(itemView.context)
                .load(topic.coverImage)
                .placeholder(R.drawable.ic_home)
                .into(topicCover)

            topicName.text = topic.name
            topicDescription.text = topic.description
            topicNewsCount.text = "${topic.newsCount} 篇文章"
            topicDiscussionCount.text = "${topic.discussionCount} 条讨论"

            itemView.setOnClickListener {
                onTopicClick(topic)
            }
        }
    }
}
