package com.example.myapplication

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newsUrl = intent.getStringExtra("news_url")
        val newsTitle = intent.getStringExtra("news_title")

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
