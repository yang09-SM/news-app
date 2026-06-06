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

class ExchangeRecordAdapter(
    private val recordList: MutableList<ExchangeRecord>
) : RecyclerView.Adapter<ExchangeRecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val pointsTextView: TextView = itemView.findViewById(R.id.pointsTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val recordImageView: ImageView = itemView.findViewById(R.id.recordImageView)

        private val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.placeholder_news)
            .error(R.drawable.placeholder_news)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(recordItem: ExchangeRecord) {
            productNameTextView.text = recordItem.productName
            pointsTextView.text = "积分：${recordItem.points}"
            timeTextView.text = formatTime(recordItem.exchangeTime)
            
            statusTextView.text = when (recordItem.status) {
                ExchangeStatus.PENDING -> "待处理"
                ExchangeStatus.PROCESSING -> "处理中"
                ExchangeStatus.COMPLETED -> "已完成"
                ExchangeStatus.CANCELLED -> "已取消"
            }

            if (recordItem.productPic.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(recordItem.productPic)
                    .apply(requestOptions)
                    .into(recordImageView)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(recordImageView)
            }
        }

        private fun formatTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exchange_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int = recordList.size

    fun updateData(newList: List<ExchangeRecord>) {
        recordList.clear()
        recordList.addAll(newList)
        notifyDataSetChanged()
    }
}
