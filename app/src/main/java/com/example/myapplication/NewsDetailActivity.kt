package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import java.util.UUID

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager
    private var isFavorited = false

    private lateinit var currentNewsId: String
    private lateinit var currentNewsTitle: String
    private lateinit var currentNewsPic: String
    private lateinit var currentNewsCategory: String
    private lateinit var currentNewsUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        currentNewsUrl = intent.getStringExtra("news_url") ?: ""
        currentNewsTitle = intent.getStringExtra("news_title") ?: ""
        currentNewsPic = intent.getStringExtra("news_pic") ?: ""
        currentNewsCategory = intent.getStringExtra("news_category") ?: "新闻"
        currentNewsId = intent.getStringExtra("news_id") ?: UUID.randomUUID().toString()

        supportActionBar?.title = currentNewsTitle

        isFavorited = prefManager.isFavorited(currentNewsId)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }

        if (currentNewsUrl.isNotEmpty()) {
            webView.loadUrl(currentNewsUrl)
        }

        saveBrowsingHistory(currentNewsId, currentNewsTitle, currentNewsPic, currentNewsCategory, currentNewsUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_detail_menu, menu)
        updateFavoriteIcon(menu?.findItem(R.id.action_favorite))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                toggleFavorite()
                return true
            }
            R.id.action_push_notify -> {
                NotificationHelper(this).requestNotificationPermission(this)

                val alarmManager = getSystemService(ALARM_SERVICE) as android.app.AlarmManager
                val intent = Intent(this, PushReceiver::class.java).apply {
                    action = "com.example.myapplication.ACTION_NEWS_PUSH"
                    putExtra("push_title", "突发新闻")
                    putExtra("push_content", currentNewsTitle.ifEmpty { "重要新闻更新" })
                    putExtra("push_url", currentNewsUrl)
                    putExtra("push_news_title", currentNewsTitle)
                }
                val triggerTime = System.currentTimeMillis() + 30_000L
                val pendingIntent = android.app.PendingIntent.getBroadcast(
                    this,
                    2001,
                    intent,
                    android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
                )

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        android.app.AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    alarmManager.set(
                        android.app.AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }

                Toast.makeText(this, "将在30秒后收到推送提醒", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_share -> {
                val shareText = "${currentNewsTitle}\n${currentNewsUrl}"
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                startActivity(Intent.createChooser(shareIntent, "分享新闻到"))
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleFavorite() {
        if (isFavorited) {
            prefManager.removeFavorite(currentNewsId)
            isFavorited = false
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show()
        } else {
            val favoriteItem = FavoriteItem(
                id = UUID.randomUUID().toString(),
                newsId = currentNewsId,
                title = currentNewsTitle,
                pic = currentNewsPic,
                category = currentNewsCategory,
                url = currentNewsUrl
            )
            prefManager.addFavorite(favoriteItem)
            isFavorited = true
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show()
        }
        invalidateOptionsMenu()
    }

    private fun updateFavoriteIcon(menuItem: MenuItem?) {
        menuItem?.let {
            if (isFavorited) {
                it.icon = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)
                it.title = "已收藏"
            } else {
                it.icon = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
                it.title = "收藏"
            }
        }
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
