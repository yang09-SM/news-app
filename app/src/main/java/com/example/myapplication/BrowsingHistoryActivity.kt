package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowsingHistoryActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var adapter: BrowsingHistoryAdapter
    private lateinit var toolbar: Toolbar
    private val historyList = mutableListOf<BrowsingHistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browsing_history)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
        loadHistory()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.historyRecyclerView)
        emptyView = findViewById(R.id.emptyView)

        adapter = BrowsingHistoryAdapter(
            historyList,
            onItemClickListener = { historyItem ->
                openNewsDetail(historyItem)
            },
            onDeleteClickListener = { historyItem, position ->
                deleteHistoryItem(historyItem, position)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BrowsingHistoryActivity)
            adapter = this@BrowsingHistoryActivity.adapter
        }
    }

    private fun loadHistory() {
        val history = prefManager.getBrowsingHistory()
        historyList.clear()
        historyList.addAll(history)
        updateUI()
    }

    private fun updateUI() {
        if (historyList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    private fun openNewsDetail(historyItem: BrowsingHistoryItem) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("news_url", historyItem.url)
        intent.putExtra("news_id", historyItem.newsId)
        intent.putExtra("news_title", historyItem.title)
        intent.putExtra("news_pic", historyItem.pic)
        intent.putExtra("news_category", historyItem.category)
        startActivity(intent)
    }

    private fun deleteHistoryItem(historyItem: BrowsingHistoryItem, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("确认删除")
            .setMessage("确定要删除这条浏览记录吗？")
            .setPositiveButton("删除") { _, _ ->
                prefManager.removeBrowsingHistory(historyItem.id)
                adapter.removeItem(position)
                updateUI()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun clearAllHistory() {
        if (historyList.isEmpty()) return

        AlertDialog.Builder(this)
            .setTitle("确认清空")
            .setMessage("确定要清空所有浏览记录吗？")
            .setPositiveButton("清空") { _, _ ->
                prefManager.clearBrowsingHistory()
                adapter.clearAll()
                updateUI()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_clear_all -> {
                clearAllHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
