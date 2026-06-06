package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CashRewardAdapter(
    private val recordList: MutableList<CashRewardRecord>
) : RecyclerView.Adapter<CashRewardAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconTextView: TextView = itemView.findViewById(R.id.iconTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        fun bind(record: CashRewardRecord) {
            descriptionTextView.text = record.description
            timeTextView.text = dateFormat.format(Date(record.time))

            val amountText = if (record.amount >= 0) "+${String.format("%.2f", record.amount)}" 
                             else String.format("%.2f", record.amount)
            amountTextView.text = amountText

            if (record.amount >= 0) {
                amountTextView.setTextColor(itemView.context.resources.getColor(R.color.quaternary_color))
            } else {
                amountTextView.setTextColor(itemView.context.resources.getColor(R.color.secondary_color))
            }

            iconTextView.text = when (record.type) {
                RewardType.READ -> "📖"
                RewardType.SHARE -> "🔗"
                RewardType.INVITE -> "👥"
                RewardType.SIGNIN -> "✅"
                RewardType.WITHDRAW -> "💰"
            }

            statusTextView.text = when (record.status) {
                RewardStatus.PENDING -> "处理中"
                RewardStatus.SUCCESS -> "已到账"
                RewardStatus.FAILED -> "失败"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cash_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int = recordList.size

    fun updateRecords(records: List<CashRewardRecord>) {
        recordList.clear()
        recordList.addAll(records)
        notifyDataSetChanged()
    }

    fun clearAll() {
        recordList.clear()
        notifyDataSetChanged()
    }
}
