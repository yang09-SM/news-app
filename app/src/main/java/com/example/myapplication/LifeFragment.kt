package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class LifeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<NewsItem>()
    private var currentStart = 0
    private val pageSize = 10
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_life, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.news_recycler_view)
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

        loadNews()
    }

    private fun loadNews() {
        isLoading = true
        ApiClient.getInstance().getNews("生活", pageSize, currentStart, object : ApiClient.ApiCallback {
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
        intent.putExtra("news_pic", newsItem.pic)
        intent.putExtra("news_category", newsItem.category)
        intent.putExtra("news_id", newsItem.title.hashCode().toString())
        startActivity(intent)
    }
}
