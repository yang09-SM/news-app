package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson

class RecommendationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var prefManager: PrefManager
    private lateinit var recommendationEngine: RecommendationEngine
    private val newsList = mutableListOf<NewsItem>()
    private val allNewsCache = mutableListOf<NewsItem>()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        recommendationEngine = RecommendationEngine(prefManager)

        recyclerView = view.findViewById(R.id.news_recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        newsAdapter = NewsAdapter(newsList) { newsItem ->
            openNewsDetail(newsItem)
        }
        
        newsAdapter.setOnItemLongClickListener { position ->
            showDislikeDialog(position)
        }

        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.primary_color)
            setOnRefreshListener {
                loadAllNewsAndRecommend()
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        loadAllNewsAndRecommend()
    }

    private fun loadAllNewsAndRecommend() {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true
        
        val categories = listOf("头条", "科技", "生活", "体育", "娱乐", "财经")
        val allFetchedNews = mutableListOf<NewsItem>()
        var completedRequests = 0

        for (category in categories) {
            ApiClient.getInstance().getNews(category, 5, 0, object : ApiClient.ApiCallback {
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
        val intent = Intent(requireContext(), NewsDetailActivity::class.java)
        intent.putExtra("news_url", newsItem.url)
        intent.putExtra("news_title", newsItem.title)
        intent.putExtra("news_pic", newsItem.pic)
        intent.putExtra("news_category", newsItem.category)
        intent.putExtra("news_id", newsItem.title.hashCode().toString())
        startActivity(intent)
    }
}
