package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChannelAdapter(
    private var channels: List<Channel>,
    private val onSubscribeClick: (Channel) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    fun updateData(newChannels: List<Channel>) {
        channels = newChannels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position])
    }

    override fun getItemCount(): Int = channels.size

    inner class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val channelIcon: ImageView = itemView.findViewById(R.id.channelIcon)
        private val channelName: TextView = itemView.findViewById(R.id.channelName)
        private val channelDescription: TextView = itemView.findViewById(R.id.channelDescription)
        private val channelNewsCount: TextView = itemView.findViewById(R.id.channelNewsCount)
        private val subscribeButton: Button = itemView.findViewById(R.id.subscribeButton)

        fun bind(channel: Channel) {
            Glide.with(itemView.context)
                .load(channel.icon)
                .placeholder(R.drawable.ic_home)
                .into(channelIcon)

            channelName.text = channel.name
            channelDescription.text = channel.description
            channelNewsCount.text = "${channel.newsCount} 篇文章"

            if (channel.isSubscribed) {
                subscribeButton.text = "已订阅"
                subscribeButton.setBackgroundColor(itemView.context.resources.getColor(R.color.gray))
            } else {
                subscribeButton.text = "订阅"
                subscribeButton.setBackgroundColor(itemView.context.resources.getColor(R.color.colorPrimary))
            }

            subscribeButton.setOnClickListener {
                onSubscribeClick(channel)
            }
        }
    }
}
