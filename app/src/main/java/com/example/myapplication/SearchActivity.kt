package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var closeButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val searchResults = mutableListOf<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
        searchEditText = findViewById(R.id.searchEditText)
        closeButton = findViewById(R.id.closeButton)
        recyclerView = findViewById(R.id.searchResultsRecyclerView)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(searchResults) { newsItem ->
            openNewsDetail(newsItem)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = newsAdapter
        }
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val query = searchEditText.text.toString().trim()
        if (query.isEmpty()) {
            Toast.makeText(this, "请输入搜索关键词", Toast.LENGTH_SHORT).show()
            return
        }

        searchNews(query)
    }

    private fun searchNews(query: String) {
        // 这里使用简单的本地搜索逻辑，实际项目中可以调用专门的搜索API
        // 我们先尝试从不同分类获取一些新闻，然后过滤
        searchResults.clear()
        newsAdapter.notifyDataSetChanged()

        val channels = listOf("头条", "科技", "生活", "体育", "娱乐", "财经")
        
        for ((index, channel) in channels.withIndex()) {
            ApiClient.getInstance().getNews(channel, 5, 0, object : ApiClient.ApiCallback {
                override fun onSuccess(response: String) {
                    runOnUiThread {
                        try {
                            val gson = Gson()
                            val newsResponse = gson.fromJson(response, NewsResponse::class.java)
                            if (newsResponse.status == "0" && newsResponse.result != null) {
                                val filteredNews = newsResponse.result.list.filter { 
                                    it.title.contains(query, ignoreCase = true)
                                }
                                searchResults.addAll(filteredNews)
                                newsAdapter.notifyDataSetChanged()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onError(error: String) {
                    // 忽略错误，继续尝试其他分类
                }
            })
        }

        Toast.makeText(this, "正在搜索: $query", Toast.LENGTH_SHORT).show()
    }

    private fun openNewsDetail(newsItem: NewsItem) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("news_url", newsItem.url)
        intent.putExtra("news_title", newsItem.title)
        startActivity(intent)
    }
}
