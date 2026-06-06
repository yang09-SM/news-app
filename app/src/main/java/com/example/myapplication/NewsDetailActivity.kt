package com.example.myapplication

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.UUID

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        val newsUrl = intent.getStringExtra("news_url")
        val newsTitle = intent.getStringExtra("news_title")
        val newsPic = intent.getStringExtra("news_pic") ?: ""
        val newsCategory = intent.getStringExtra("news_category") ?: "新闻"
        val newsId = intent.getStringExtra("news_id") ?: UUID.randomUUID().toString()

        supportActionBar?.title = newsTitle

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }

        newsUrl?.let {
            webView.loadUrl(it)
        }

        saveBrowsingHistory(newsId, newsTitle ?: "", newsPic, newsCategory, newsUrl ?: "")
    }

    private fun saveBrowsingHistory(newsId: String, title: String, pic: String, category: String, url: String) {
        val historyItem = BrowsingHistoryItem(
            id = UUID.randomUUID().toString(),
            newsId = newsId,
            title = title,
            pic = pic,
            category = category,
            url = url,
            browseTime = System.currentTimeMillis(),
            readDuration = 0
        )
        prefManager.addBrowsingHistory(historyItem)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
