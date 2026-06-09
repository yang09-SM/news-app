package com.example.myapplication

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class OfflineReadingActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: OfflineNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_reading)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "离线阅读"

        prefManager = PrefManager(this)

        swipeRefresh = findViewById(R.id.swipeRefresh)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)

        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OfflineNewsAdapter(
            this,
            mutableListOf(),
            onNewsClick = { news ->
                openOfflineNews(news)
            },
            onDeleteClick = { news ->
                showDeleteConfirmation(news)
            }
        )
        newsRecyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            loadOfflineNews()
        }

        loadOfflineNews()
    }

    private fun loadOfflineNews() {
        val offlineNews = prefManager.getOfflineNews()
        adapter.updateData(offlineNews)
        swipeRefresh.isRefreshing = false
    }

    private fun openOfflineNews(news: OfflineNewsItem) {
        val dialogView = layoutInflater.inflate(R.layout.activity_news_detail, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val webView = dialogView.findViewById<WebView>(R.id.webview)
        val dialogToolbar = dialogView.findViewById<Toolbar>(R.id.toolbar)

        dialogToolbar.title = news.title
        dialogToolbar.setNavigationOnClickListener {
            dialog.dismiss()
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return true
            }
        }

        if (news.content != null) {
            webView.loadDataWithBaseURL(null, news.content, "text/html", "UTF-8", null)
        } else {
            webView.loadUrl(news.url)
        }

        dialog.show()
    }

    private fun showDeleteConfirmation(news: OfflineNewsItem) {
        AlertDialog.Builder(this)
            .setTitle("删除离线新闻")
            .setMessage("确定要删除\"${news.title}\"吗？")
            .setPositiveButton("删除") { _, _ ->
                prefManager.removeOfflineNews(news.id)
                adapter.removeNews(news)
                Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
