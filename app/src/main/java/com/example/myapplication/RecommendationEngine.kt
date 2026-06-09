package com.example.myapplication

import android.content.Context
import org.json.JSONObject

class RecommendationEngine(private val prefManager: PrefManager) {

    /**
     * 本地推荐算法（离线降级方案）
     */
    fun getRecommendedNews(allNews: List<NewsItem>): List<NewsItem> {
        if (allNews.isEmpty()) return emptyList()

        val dislikedNewsIds = prefManager.getDislikedNews()
        val browsingHistory = prefManager.getBrowsingHistory()
        val favorites = prefManager.getFavorites()

        // 过滤掉不感兴趣的新闻
        val filteredNews = allNews.filter { !dislikedNewsIds.contains(it.url) }

        // 计算每个新闻的得分
        val scoredNews = filteredNews.map { news ->
            var score = 0.0

            // 基于浏览历史的兴趣标签匹配
            val browsingCategories = browsingHistory.map { it.category }.toSet()
            if (browsingCategories.contains(news.category)) {
                score += 2.0
            }

            // 基于收藏的兴趣匹配
            val favoriteCategories = favorites.map { it.category }.toSet()
            if (favoriteCategories.contains(news.category)) {
                score += 3.0
            }

            // 标题关键词匹配
            val historyKeywords = browsingHistory.flatMap { it.title.split(" ", "，", "。") }
                .filter { it.length >= 2 }
                .groupBy { it }
                .mapValues { it.value.size }
                .toList()
                .sortedByDescending { it.second }
                .take(10)
                .map { it.first }

            for (keyword in historyKeywords) {
                if (news.title.contains(keyword)) {
                    score += 1.0
                }
            }

            // 时间因素（越新的新闻权重越高）
            score += 0.5

            news to score
        }

        // 按得分排序，取前20个
        return scoredNews
            .sortedByDescending { it.second }
            .take(20)
            .map { it.first }
    }

    fun addDislikedNews(newsUrl: String) {
        prefManager.addDislikedNews(newsUrl)
    }

    /**
     * 从服务端获取推荐新闻（在线方案）
     */
    fun getRecommendedNewsFromServer(context: Context, userId: Long? = null, callback: (List<NewsItem>?) -> Unit) {
        val apiClient = ApiClient.getInstance(context)
        apiClient.getRecommendedNews(userId, object : ApiClient.ApiCallback {
            override fun onSuccess(response: String) {
                try {
                    val json = JSONObject(response)
                    val code = json.optInt("code", -1)
                    if (code == 200) {
                        val data = json.optJSONArray("data")
                        if (data != null) {
                            val newsList = mutableListOf<NewsItem>()
                            for (i in 0 until data.length()) {
                                val item = data.getJSONObject(i)
                                newsList.add(NewsItem(
                                    url = item.optString("articleId", ""),
                                    title = item.optString("title", ""),
                                    category = item.optString("categoryId", ""),
                                    pic = item.optString("coverImage", ""),
                                    time = item.optString("createTime", ""),
                                    content = item.optString("content", "")
                                ))
                            }
                            callback(newsList)
                            return
                        }
                    }
                    callback(null)
                } catch (e: Exception) {
                    callback(null)
                }
            }

            override fun onError(error: String) {
                callback(null)
            }
        })
    }
}
