package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("push_title") ?: "新闻推送"
        val content = intent.getStringExtra("push_content") ?: "您有新的新闻资讯"
        val newsUrl = intent.getStringExtra("push_url") ?: ""
        val newsTitle = intent.getStringExtra("push_news_title") ?: title

        val helper = NotificationHelper(context)
        helper.sendNewsNotification(title, content, newsUrl, newsTitle)
    }
}
