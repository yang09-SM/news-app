package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CashRewardActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var toolbar: Toolbar
    private lateinit var balanceTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var withdrawButton: Button
    private lateinit var taskButton: Button
    private lateinit var adapter: CashRewardAdapter
    private val recordList = mutableListOf<CashRewardRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_reward)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
        loadData()
    }

    private fun initViews() {
        balanceTextView = findViewById(R.id.balanceTextView)
        recyclerView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.emptyView)
        withdrawButton = findViewById(R.id.withdrawButton)
        taskButton = findViewById(R.id.taskButton)

        adapter = CashRewardAdapter(recordList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CashRewardActivity)
            adapter = this@CashRewardActivity.adapter
            isNestedScrollingEnabled = false
        }

        withdrawButton.setOnClickListener {
            handleWithdraw()
        }

        taskButton.setOnClickListener {
            handleTask()
        }
    }

    private fun loadData() {
        val balance = prefManager.getCashBalance()
        balanceTextView.text = String.format("%.2f", balance)

        val records = prefManager.getCashRewardRecords()
        recordList.clear()
        recordList.addAll(records)
        adapter.updateRecords(records)

        updateUI()
    }

    private fun updateUI() {
        if (recordList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
    }

    private fun handleWithdraw() {
        val balance = prefManager.getCashBalance()
        if (balance <= 0) {
            Toast.makeText(this, "余额不足，无法提现", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this, "提现功能开发中...", Toast.LENGTH_SHORT).show()
    }

    private fun handleTask() {
        Toast.makeText(this, "任务中心开发中...", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
