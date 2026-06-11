package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson

class HomeNewsFragment : Fragment() {

    private lateinit var categoryScrollView: HorizontalScrollView
    private lateinit var categoryContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var prefManager: PrefManager
    private lateinit var recommendationEngine: RecommendationEngine
    private val newsList = mutableListOf<NewsItem>()
    private val allNewsCache = mutableListOf<NewsItem>()
    private var currentCategory = "推荐"
    private var currentStart = 0
    private val pageSize = 10
    private var isLoading = false
    private val categories = listOf("推荐", "科技", "生活", "体育", "娱乐", "财经", "本地")
    private val channelMap = mapOf(
        "推荐" to "头条",
        "科技" to "科技",
        "生活" to "生活",
        "体育" to "体育",
        "娱乐" to "娱乐",
        "财经" to "财经",
        "本地" to "头条"
    )
    private var currentCity = "北京"
    private var isLocationPermissionGranted = false
    
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isLocationPermissionGranted = isGranted
        if (isGranted) {
            showLocationSuccessDialog()
        } else {
            showLocationDeniedDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        recommendationEngine = RecommendationEngine(prefManager)
        initViews(view)
        setupCategoryTabs()
        setupRecyclerView()
        loadNews()
    }

    private fun initViews(view: View) {
        categoryScrollView = view.findViewById(R.id.categoryScrollView)
        categoryContainer = view.findViewById(R.id.categoryContainer)
        recyclerView = view.findViewById(R.id.newsRecyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)

        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.primary_color)
            setOnRefreshListener {
                clearAndLoadNews()
            }
        }
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
        
        newsAdapter.setOnItemLongClickListener { position ->
            if (currentCategory == "推荐") {
                showDislikeDialog(position)
            }
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
        if (currentCategory == "推荐") {
            loadAllNewsAndRecommend()
        } else if (currentCategory == "本地") {
            checkLocationPermissionAndLoadLocalNews()
        } else {
            loadCategoryNews()
        }
    }
    
    private fun checkLocationPermissionAndLoadLocalNews() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isLocationPermissionGranted = true
            loadLocalNews()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    
    private fun loadLocalNews() {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true
        
        ApiClient.getInstance(requireContext()).getNews("头条", pageSize, currentStart, object : ApiClient.ApiCallback {
            override fun onSuccess(response: String) {
                activity?.runOnUiThread {
                    try {
                        val gson = Gson()
                        val newsResponse = gson.fromJson(response, NewsResponse::class.java)
                        if (newsResponse.status == "0" && newsResponse.result != null) {
                            // 为本地新闻添加城市标识
                            val localNews = newsResponse.result.list.map { 
                                it.copy(title = "【$currentCity】${it.title}")
                            }
                            newsAdapter.addNews(localNews)
                            currentStart += pageSize
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun onError(error: String) {
                activity?.runOnUiThread {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }
    
    private fun showLocationSuccessDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("定位成功")
            .setMessage("已定位到 $currentCity，正在为您加载本地新闻")
            .setPositiveButton("确定") { _, _ ->
                loadLocalNews()
            }
            .show()
    }
    
    private fun showLocationDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("需要位置权限")
            .setMessage("为了给您提供本地新闻服务，需要访问您的位置信息。您也可以手动选择城市。")
            .setPositiveButton("去设置") { _, _ ->
                // 这里可以打开应用设置页面
                Toast.makeText(requireContext(), "请在设置中开启位置权限", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("手动选择") { _, _ ->
                showCitySelectionDialog()
            }
            .show()
    }
    
    private fun showCitySelectionDialog() {
        val cities = arrayOf("北京", "上海", "广州", "深圳", "杭州", "南京", "成都", "武汉")
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("选择城市")
            .setItems(cities) { _, which ->
                currentCity = cities[which]
                loadLocalNews()
            }
            .show()
    }

    private fun loadCategoryNews() {
        isLoading = true
        val channel = channelMap[currentCategory] ?: "头条"
        
        ApiClient.getInstance(requireContext()).getNews(channel, pageSize, currentStart, object : ApiClient.ApiCallback {
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
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun onError(error: String) {
                activity?.runOnUiThread {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun loadAllNewsAndRecommend() {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true
        
        val categories = listOf("头条", "科技", "生活", "体育", "娱乐", "财经")
        val allFetchedNews = mutableListOf<NewsItem>()
        var completedRequests = 0

        for (category in categories) {
            ApiClient.getInstance(requireContext()).getNews(category, 5, 0, object : ApiClient.ApiCallback {
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
                        activity?.runOnUiThread {
                            allNewsCache.clear()
                            allNewsCache.addAll(allFetchedNews)
                            showRecommendations()
                            isLoading = false
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }

                override fun onError(error: String) {
                    completedRequests++
                    if (completedRequests == categories.size) {
                        activity?.runOnUiThread {
                            showRecommendations()
                            isLoading = false
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            })
        }
    }

    private fun showRecommendations() {
        val recommendedNews = recommendationEngine.getRecommendedNews(allNewsCache)
        newsAdapter.clearNews()
        newsAdapter.addNews(recommendedNews)
    }

    private fun loadMoreNews() {
        if (currentCategory == "推荐") {
            // 推荐页面不支持加载更多
        } else if (currentCategory == "本地") {
            loadLocalNews()
        } else {
            loadCategoryNews()
        }
    }

    private fun showDislikeDialog(position: Int) {
        val newsItem = newsList[position]
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("不感兴趣")
            .setMessage("确定不再推荐类似内容吗？")
            .setPositiveButton("确定") { _, _ ->
                recommendationEngine.addDislikedNews(newsItem.url)
                newsList.removeAt(position)
                newsAdapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "已减少此类内容推荐", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun openNewsDetail(newsItem: NewsItem) {
        when (newsItem.type) {
            "video" -> {
                val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
                intent.putExtra("video_url", newsItem.videoUrl)
                intent.putExtra("news_title", newsItem.title)
                intent.putExtra("news_src", newsItem.src)
                intent.putExtra("news_time", newsItem.time)
                startActivity(intent)
            }
            "audio" -> {
                val intent = Intent(requireContext(), AudioPlayerActivity::class.java)
                intent.putExtra("audio_url", newsItem.audioUrl)
                intent.putExtra("news_title", newsItem.title)
                intent.putExtra("news_src", newsItem.src)
                intent.putExtra("news_time", newsItem.time)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(requireContext(), NewsDetailActivity::class.java)
                intent.putExtra("news_url", newsItem.url)
                intent.putExtra("news_title", newsItem.title)
                intent.putExtra("news_pic", newsItem.pic)
                intent.putExtra("news_category", newsItem.category)
                intent.putExtra("news_id", newsItem.title.hashCode().toString())
                startActivity(intent)
            }
        }
    }
}
