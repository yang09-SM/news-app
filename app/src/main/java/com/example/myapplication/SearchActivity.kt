package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var closeButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var prefManager: PrefManager
    private lateinit var searchSuggestions: LinearLayout
    private lateinit var searchFilters: LinearLayout
    private lateinit var hotSearchChips: LinearLayout
    private lateinit var historySearchChips: LinearLayout
    private lateinit var clearHistoryBtn: TextView
    private lateinit var timeFilterSpinner: Spinner
    private lateinit var hotFilterSpinner: Spinner

    private val searchResults = mutableListOf<NewsItem>()
    private val allFetchedNews = mutableListOf<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        prefManager = PrefManager(this)
        initViews()
        setupRecyclerView()
        setupSearchChips()
        setupFilters()
        setupListeners()
    }

    private fun initViews() {
        searchEditText = findViewById(R.id.searchEditText)
        closeButton = findViewById(R.id.closeButton)
        recyclerView = findViewById(R.id.searchResultsRecyclerView)
        searchSuggestions = findViewById(R.id.searchSuggestions)
        searchFilters = findViewById(R.id.searchFilters)
        hotSearchChips = findViewById(R.id.hotSearchChips)
        historySearchChips = findViewById(R.id.historySearchChips)
        clearHistoryBtn = findViewById(R.id.clearHistoryBtn)
        timeFilterSpinner = findViewById(R.id.timeFilterSpinner)
        hotFilterSpinner = findViewById(R.id.hotFilterSpinner)
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

    private fun setupSearchChips() {
        val hotSearches = prefManager.getHotSearch()
        hotSearches.forEachIndexed { index, keyword ->
            addChip(hotSearchChips, "${index + 1}. $keyword", keyword)
        }

        updateHistoryChips()
    }

    private fun updateHistoryChips() {
        historySearchChips.removeAllViews()
        val history = prefManager.getSearchHistory()
        history.forEach { keyword ->
            addChip(historySearchChips, keyword, keyword)
        }
    }

    private fun addChip(container: LinearLayout, text: String, keyword: String) {
        val chip = TextView(this).apply {
            this.text = text
            setPadding(32, 16, 32, 16)
            setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 8, 0)
            }
            gravity = Gravity.CENTER
            setOnClickListener {
                searchEditText.setText(keyword)
                searchEditText.setSelection(keyword.length)
                performSearch(keyword)
            }
        }
        container.addView(chip)
    }

    private fun setupFilters() {
        val timeOptions = arrayOf("全部时间", "最新", "本周", "本月")
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeFilterSpinner.adapter = timeAdapter

        val hotOptions = arrayOf("综合排序", "最多浏览", "最多评论")
        val hotAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hotOptions)
        hotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hotFilterSpinner.adapter = hotAdapter

        timeFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (searchResults.isNotEmpty()) {
                    filterAndDisplayResults(searchEditText.text.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        hotFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (searchResults.isNotEmpty()) {
                    filterAndDisplayResults(searchEditText.text.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }

        clearHistoryBtn.setOnClickListener {
            prefManager.clearSearchHistory()
            updateHistoryChips()
        }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.text.toString())
                true
            } else {
                false
            }
        }

        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch(searchEditText.text.toString())
                true
            } else {
                false
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    showSuggestions()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showSuggestions() {
        searchSuggestions.visibility = View.VISIBLE
        searchFilters.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            return
        }

        prefManager.addSearchHistory(query)
        updateHistoryChips()

        searchSuggestions.visibility = View.GONE
        searchFilters.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE

        searchResults.clear()
        newsAdapter.notifyDataSetChanged()

        fetchAllNewsAndSearch(query)
    }

    private fun fetchAllNewsAndSearch(query: String) {
        val categories = listOf("头条", "科技", "生活", "体育", "娱乐", "财经")
        allFetchedNews.clear()
        var completedRequests = 0

        for (category in categories) {
            ApiClient.getInstance().getNews(category, 10, 0, object : ApiClient.ApiCallback {
                override fun onSuccess(response: String) {
                    try {
                        val gson = Gson()
                        val newsResponse = gson.fromJson(response, NewsResponse::class.java)
                        if (newsResponse.status == "0" && newsResponse.result != null) {
                            newsResponse.result.list.forEach { 
                                it.category = category
                            }
                            synchronized(allFetchedNews) {
                                allFetchedNews.addAll(newsResponse.result.list)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    completedRequests++
                    if (completedRequests == categories.size) {
                        runOnUiThread {
                            filterAndDisplayResults(query)
                        }
                    }
                }

                override fun onError(error: String) {
                    completedRequests++
                    if (completedRequests == categories.size) {
                        runOnUiThread {
                            filterAndDisplayResults(query)
                        }
                    }
                }
            })
        }
    }

    private fun filterAndDisplayResults(query: String) {
        var filteredNews = allFetchedNews.filter { 
            it.title.contains(query, ignoreCase = true) 
        }.toMutableList()

        val timeFilterPosition = timeFilterSpinner.selectedItemPosition
        val hotFilterPosition = hotFilterSpinner.selectedItemPosition

        when (hotFilterPosition) {
            1 -> filteredNews.sortByDescending { it.title.length }
            2 -> filteredNews.shuffle()
        }

        searchResults.clear()
        searchResults.addAll(filteredNews)
        newsAdapter.notifyDataSetChanged()
    }

    private fun openNewsDetail(newsItem: NewsItem) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("news_url", newsItem.url)
        intent.putExtra("news_title", newsItem.title)
        intent.putExtra("news_pic", newsItem.pic)
        intent.putExtra("news_category", newsItem.category)
        intent.putExtra("news_id", newsItem.title.hashCode().toString())
        startActivity(intent)
    }
}
