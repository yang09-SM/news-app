package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class HomeNewsFragment : Fragment() {

    private lateinit var categoryScrollView: HorizontalScrollView
    private lateinit var categoryContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<NewsItem>()
    private var currentCategory = "推荐"
    private var currentStart = 0
    private val pageSize = 10
    private var isLoading = false
    private val categories = listOf("推荐", "科技", "生活", "体育", "娱乐", "财经")
    private val channelMap = mapOf(
        "推荐" to "头条",
        "科技" to "科技",
        "生活" to "生活",
        "体育" to "体育",
        "娱乐" to "娱乐",
        "财经" to "财经"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupCategoryTabs()
        setupRecyclerView()
        loadNews()
    }

    private fun initViews(view: View) {
        categoryScrollView = view.findViewById(R.id.categoryScrollView)
        categoryContainer = view.findViewById(R.id.categoryContainer)
        recyclerView = view.findViewById(R.id.newsRecyclerView)
    }

    private fun setupCategoryTabs() {
        categoryContainer.removeAllViews()
        
        for (category in categories) {
            val tabView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_category_tab, categoryContainer, false)
            
            val textView = tabView.findViewById<TextView>(R.id.categoryTextView)
            textView.text = category
            
            updateTabStyle(textView, category == currentCategory)
            
            tabView.setOnClickListener {
                if (currentCategory != category) {
                    currentCategory = category
                    updateAllTabs()
                    clearAndLoadNews()
                }
            }
            
            categoryContainer.addView(tabView)
        }
    }

    private fun updateAllTabs() {
        for (i in 0 until categoryContainer.childCount) {
            val tabView = categoryContainer.getChildAt(i)
            val textView = tabView.findViewById<TextView>(R.id.categoryTextView)
            val category = categories[i]
            updateTabStyle(textView, category == currentCategory)
        }
    }

    private fun updateTabStyle(textView: TextView, isSelected: Boolean) {
        if (isSelected) {
            textView.setBackgroundResource(R.drawable.category_tab_selected)
            textView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        } else {
            textView.setBackgroundResource(R.drawable.category_tab_normal)
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(newsList) { newsItem ->
            openNewsDetail(newsItem)
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 2) {
                        loadMoreNews()
                    }
                }
            })
        }
    }

    private fun clearAndLoadNews() {
        currentStart = 0
        newsList.clear()
        newsAdapter.notifyDataSetChanged()
        loadNews()
    }

    private fun loadNews() {
        isLoading = true
        val channel = channelMap[currentCategory] ?: "头条"
        
        ApiClient.getInstance().getNews(channel, pageSize, currentStart, object : ApiClient.ApiCallback {
            override fun onSuccess(response: String) {
                activity?.runOnUiThread {
                    try {
                        val gson = Gson()
                        val newsResponse = gson.fromJson(response, NewsResponse::class.java)
                        if (newsResponse.status == "0" && newsResponse.result != null) {
                            newsAdapter.addNews(newsResponse.result.list)
                            currentStart += pageSize
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    isLoading = false
                }
            }

            override fun onError(error: String) {
                activity?.runOnUiThread {
                    isLoading = false
                }
            }
        })
    }

    private fun loadMoreNews() {
        loadNews()
    }

    private fun openNewsDetail(newsItem: NewsItem) {
        val intent = Intent(requireContext(), NewsDetailActivity::class.java)
        intent.putExtra("news_url", newsItem.url)
        intent.putExtra("news_title", newsItem.title)
        startActivity(intent)
    }
}
