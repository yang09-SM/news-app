package com.example.myapplication

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ReportAdapter(
    private val reportsList: MutableList<ReportItem>,
    private val onItemClickListener: (ReportItem) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.reportTitle)
        private val contentTextView: TextView = itemView.findViewById(R.id.reportContent)
        private val categoryTextView: TextView = itemView.findViewById(R.id.reportCategory)
        private val locationTextView: TextView = itemView.findViewById(R.id.reportLocation)
        private val timeTextView: TextView = itemView.findViewById(R.id.reportTime)
        private val statusTextView: TextView = itemView.findViewById(R.id.reportStatus)

        fun bind(report: ReportItem) {
            titleTextView.text = report.title
            contentTextView.text = report.content
            categoryTextView.text = report.category
            locationTextView.text = report.location.ifEmpty { "未填写地点" }
            timeTextView.text = formatTime(report.createTime)

            val statusInfo = getStatusInfo(report.status)
            statusTextView.text = statusInfo.first
            statusTextView.setBackgroundColor(statusInfo.second)
            statusTextView.setTextColor(android.graphics.Color.WHITE)

            val statusDrawable = statusTextView.background as? GradientDrawable
            statusDrawable?.cornerRadius = 8f

            itemView.setOnClickListener {
                onItemClickListener(report)
            }
        }

        private fun getStatusInfo(status: ReportStatus): Pair<String, Int> {
            return when (status) {
                ReportStatus.SUBMITTED -> Pair("已提交", ContextCompat.getColor(itemView.context, R.color.tertiary_color))
                ReportStatus.REVIEWING -> Pair("处理中", ContextCompat.getColor(itemView.context, R.color.primary_color))
                ReportStatus.ACCEPTED -> Pair("已采纳", ContextCompat.getColor(itemView.context, R.color.quaternary_color))
                ReportStatus.REJECTED -> Pair("已驳回", ContextCompat.getColor(itemView.context, R.color.secondary_color))
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
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    sdf.format(Date(timestamp))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(reportsList[position])
    }

    override fun getItemCount(): Int = reportsList.size

    fun updateData(newList: List<ReportItem>) {
        reportsList.clear()
        reportsList.addAll(newList)
        notifyDataSetChanged()
    }
}
