package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FavoritesFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var notLoggedInLayout: LinearLayout
    private lateinit var loginButton: Button
    private lateinit var favoritesAdapter: NewsAdapter
    private val favoriteNewsList = mutableListOf<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())

        initViews(view)
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.favoritesRecyclerView)
        emptyView = view.findViewById(R.id.emptyView)
        notLoggedInLayout = view.findViewById(R.id.notLoggedInLayout)
        loginButton = view.findViewById(R.id.loginButton)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        favoritesAdapter = NewsAdapter(favoriteNewsList) { newsItem ->
            openNewsDetail(newsItem)
        }
        recyclerView.adapter = favoritesAdapter

        favoritesAdapter.setOnItemLongClickListener { position ->
            val item = favoriteNewsList[position]
            showRemoveFavoriteDialog(item, position)
            true
        }
    }

    private fun updateUI() {
        if (prefManager.isLoggedIn()) {
            notLoggedInLayout.visibility = View.GONE
            loadFavorites()
        } else {
            notLoggedInLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.GONE
            setupNotLoggedInViews()
        }
    }

    private fun loadFavorites() {
        val favorites = prefManager.getFavorites()
        favoriteNewsList.clear()
        favoriteNewsList.addAll(favorites.map { favorite ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val timeStr = dateFormat.format(Date(favorite.favoriteTime))
            NewsItem(
                title = favorite.title,
                time = timeStr,
                src = favorite.category,
                category = favorite.category,
                url = favorite.url,
                pic = favorite.pic,
                content = null
            )
        })

        if (favoriteNewsList.isEmpty()) {
            showEmptyState()
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            favoritesAdapter.notifyDataSetChanged()
        }
    }

    private fun showEmptyState() {
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    private fun setupNotLoggedInViews() {
        loginButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
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

    private fun showRemoveFavoriteDialog(newsItem: NewsItem, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("取消收藏")
            .setMessage("确定要取消收藏「${newsItem.title}」吗？")
            .setPositiveButton("确定") { _, _ ->
                val favorites = prefManager.getFavorites()
                if (position < favorites.size) {
                    prefManager.removeFavorite(favorites[position].newsId)
                    loadFavorites()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
