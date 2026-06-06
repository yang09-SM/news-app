package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AchievementAdapter(
    private val achievementList: List<AchievementItem>
) : RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    inner class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medalBackground: ImageView = itemView.findViewById(R.id.medalBackground)
        val medalIcon: TextView = itemView.findViewById(R.id.medalIcon)
        val achievementName: TextView = itemView.findViewById(R.id.achievementName)
        val achievementPoints: TextView = itemView.findViewById(R.id.achievementPoints)
        val achievementDescription: TextView = itemView.findViewById(R.id.achievementDescription)
        val progressContainer: LinearLayout = itemView.findViewById(R.id.progressContainer)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val progressText: TextView = itemView.findViewById(R.id.progressText)
        val unlockTime: TextView = itemView.findViewById(R.id.unlockTime)

        fun bind(achievement: AchievementItem) {
            achievementName.text = achievement.name
            achievementDescription.text = achievement.description
            achievementPoints.text = "+${achievement.points}"
            medalIcon.text = achievement.icon

            if (achievement.isUnlocked) {
                medalBackground.setImageResource(R.drawable.medal_gold)
                progressContainer.visibility = View.GONE
                unlockTime.visibility = View.VISIBLE
                unlockTime.text = "已获得 · ${formatTime(achievement.unlockTime)}"
            } else {
                medalBackground.setImageResource(R.drawable.medal_gray)
                progressContainer.visibility = View.VISIBLE
                unlockTime.visibility = View.GONE

                val progressPercent = if (achievement.target > 0) {
                    (achievement.progress * 100 / achievement.target).coerceAtMost(100)
                } else {
                    0
                }

                progressBar.progress = progressPercent
                progressText.text = "${achievement.progress}/${achievement.target}"
            }
        }

        private fun formatTime(timestamp: Long?): String {
            if (timestamp == null) return ""
            val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(achievementList[position])
    }

    override fun getItemCount(): Int = achievementList.size
}
