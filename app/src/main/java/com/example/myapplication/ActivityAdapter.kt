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
import java.util.Locale

class ActivityAdapter(
    private val activityList: MutableList<ActivityItem>,
    private val onActivityClickListener: (ActivityItem) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityCoverImageView: ImageView = itemView.findViewById(R.id.activityCoverImageView)
        val activityTitleTextView: TextView = itemView.findViewById(R.id.activityTitleTextView)
        val activityDescriptionTextView: TextView = itemView.findViewById(R.id.activityDescriptionTextView)
        val activityTimeTextView: TextView = itemView.findViewById(R.id.activityTimeTextView)
        val activityLocationTextView: TextView = itemView.findViewById(R.id.activityLocationTextView)
        val activityStatusTextView: TextView = itemView.findViewById(R.id.activityStatusTextView)
        val participantCountTextView: TextView = itemView.findViewById(R.id.participantCountTextView)
        val registeredStatusTextView: TextView = itemView.findViewById(R.id.registeredStatusTextView)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        fun bind(activityItem: ActivityItem) {
            activityTitleTextView.text = activityItem.title
            activityDescriptionTextView.text = activityItem.description
            activityTimeTextView.text = dateFormat.format(activityItem.startTime)
            activityLocationTextView.text = activityItem.location
            participantCountTextView.text = "${activityItem.participantCount}人"

            activityStatusTextView.text = when (activityItem.status) {
                ActivityStatus.UPCOMING -> "即将开始"
                ActivityStatus.ONGOING -> "进行中"
                ActivityStatus.ENDED -> "已结束"
            }

            registeredStatusTextView.visibility = if (activityItem.isRegistered) View.VISIBLE else View.GONE

            if (activityItem.cover.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(activityItem.cover)
                    .apply(requestOptions)
                    .into(activityCoverImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(activityCoverImageView)
            }

            itemView.setOnClickListener {
                onActivityClickListener(activityItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activityList[position])
    }

    override fun getItemCount(): Int = activityList.size

    fun updateData(newList: List<ActivityItem>) {
        activityList.clear()
        activityList.addAll(newList)
        notifyDataSetChanged()
    }
}