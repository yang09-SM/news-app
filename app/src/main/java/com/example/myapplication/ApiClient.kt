package com.example.myapplication

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient private constructor(private val context: Context) {
    private val client: OkHttpClient

    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val BASE_URL = "http://10.0.2.2:80"

    interface ApiCallback {
        fun onSuccess(response: String)
        fun onError(error: String)
    }

    init {
        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val prefManager = PrefManager(context)
                val token = prefManager.getAuthToken()
                val request = if (token.isNotEmpty()) {
                    chain.request().newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(request)
            }
            .build()
    }

    // ==================== 认证相关 ====================

    fun register(username: String, password: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/auth/register")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun login(username: String, password: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/auth/login")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun changePassword(username: String, oldPassword: String, newPassword: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("username", username)
            put("oldPassword", oldPassword)
            put("newPassword", newPassword)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/auth/change-password")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun refreshToken(refreshToken: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("refreshToken", refreshToken)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/auth/refresh-token")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 新闻相关 ====================

    fun getNews(channel: String, num: Int, start: Int, callback: ApiCallback) {
        val appKey = "ad0a92a8caec9d76"
        val urlBuilder = HttpUrl.Builder()
            .scheme("https")
            .host("api.jisuapi.com")
            .addPathSegment("news")
            .addPathSegment("get")
            .addQueryParameter("channel", channel)
            .addQueryParameter("num", num.toString())
            .addQueryParameter("start", start.toString())
            .addQueryParameter("appkey", appKey)

        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getNewsList(pageNum: Int, pageSize: Int, categoryId: Long? = null, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/news/list".toHttpUrl().newBuilder()
            .addQueryParameter("pageNum", pageNum.toString())
            .addQueryParameter("pageSize", pageSize.toString())
        if (categoryId != null) {
            urlBuilder.addQueryParameter("categoryId", categoryId.toString())
        }
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getNewsDetail(articleId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getHotNews(callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/hot")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getNewsByCategory(categoryId: Long, pageNum: Int, pageSize: Int, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/news/category/$categoryId".toHttpUrl().newBuilder()
            .addQueryParameter("pageNum", pageNum.toString())
            .addQueryParameter("pageSize", pageSize.toString())
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun searchNews(keyword: String, pageNum: Int, pageSize: Int, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/news/search".toHttpUrl().newBuilder()
            .addQueryParameter("keyword", keyword)
            .addQueryParameter("pageNum", pageNum.toString())
            .addQueryParameter("pageSize", pageSize.toString())
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getRecommendedNews(userId: Long? = null, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/news/recommended".toHttpUrl().newBuilder()
        if (userId != null) {
            urlBuilder.addQueryParameter("userId", userId.toString())
        }
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun incrementViewCount(articleId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/view")
            .post("".toRequestBody(JSON))
            .build()
        executeRequest(request, callback)
    }

    // ==================== 分类相关 ====================

    fun getCategories(callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/category/list")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getAllCategories(callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/category/all")
            .get()
            .build()
        executeRequest(request, callback)
    }

    // ==================== 收藏相关 ====================

    fun getFavorites(userId: Long, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/news/favorites".toHttpUrl().newBuilder()
            .addQueryParameter("userId", userId.toString())
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun addFavorite(articleId: Long, userId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/favorite")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun removeFavorite(articleId: Long, userId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/favorite")
            .delete(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 点赞相关 ====================

    fun addLike(articleId: Long, userId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/like")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun removeLike(articleId: Long, userId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/like")
            .delete(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 评论相关 ====================

    fun getComments(articleId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/comments")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun addComment(articleId: Long, userId: Long, userName: String, content: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
            put("userName", userName)
            put("content", content)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/$articleId/comments")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun deleteComment(commentId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/comments/$commentId")
            .delete()
            .build()
        executeRequest(request, callback)
    }

    fun likeComment(commentId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/news/comments/$commentId/like")
            .post("".toRequestBody(JSON))
            .build()
        executeRequest(request, callback)
    }

    fun replyComment(commentId: Long, userId: Long, userName: String, content: String, replyToUserId: Long? = null, replyToUserName: String? = null, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
            put("userName", userName)
            put("content", content)
            if (replyToUserId != null) put("replyToUserId", replyToUserId)
            if (replyToUserName != null) put("replyToUserName", replyToUserName)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/news/comments/$commentId/reply")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 用户画像相关 ====================

    fun getUserProfile(userId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/user/profile/$userId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun updateUserProfile(userId: Long, profile: JSONObject, callback: ApiCallback) {
        val requestBody = profile.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/profile")
            .put(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 用户兴趣标签 ====================

    fun getUserInterests(userId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/user/interests/$userId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun updateUserInterests(userId: Long, interests: JSONObject, callback: ApiCallback) {
        val requestBody = interests.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/interests/$userId")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 浏览历史 ====================

    fun getBrowsingHistory(userId: Long, pageNum: Int, pageSize: Int, callback: ApiCallback) {
        val urlBuilder = "$BASE_URL/api/user/history/$userId".toHttpUrl().newBuilder()
            .addQueryParameter("pageNum", pageNum.toString())
            .addQueryParameter("pageSize", pageSize.toString())
        val request = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun addBrowsingHistory(userId: Long, articleId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
            put("articleId", articleId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/history")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    // ==================== 关注相关 ====================

    fun followUser(userId: Long, followUserId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
            put("followUserId", followUserId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/follow")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun unfollowUser(userId: Long, followUserId: Long, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("userId", userId)
            put("followUserId", followUserId)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/follow")
            .delete(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun getFollowers(userId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/user/followers/$userId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    fun getFollowing(userId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/user/following/$userId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    // ==================== 数据同步 ====================

    fun syncDataToCloud(userId: Long, data: JSONObject, callback: ApiCallback) {
        val requestBody = data.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/user/sync/$userId")
            .post(requestBody)
            .build()
        executeRequest(request, callback)
    }

    fun syncDataFromCloud(userId: Long, callback: ApiCallback) {
        val request = Request.Builder()
            .url("$BASE_URL/api/user/sync/$userId")
            .get()
            .build()
        executeRequest(request, callback)
    }

    // ==================== 通用请求 ====================

    private fun executeRequest(request: Request, callback: ApiCallback) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "网络请求失败")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    callback.onSuccess(responseBody)
                } else if (response.code == 401) {
                    // Token过期，尝试刷新
                    tryRefreshToken(callback, responseBody)
                } else {
                    callback.onError(responseBody)
                }
            }
        })
    }

    private fun tryRefreshToken(originalCallback: ApiCallback, originalError: String) {
        val prefManager = PrefManager(context)
        val refreshToken = prefManager.getRefreshToken()
        if (refreshToken.isEmpty()) {
            originalCallback.onError(originalError)
            return
        }

        val json = JSONObject().apply {
            put("refreshToken", refreshToken)
        }
        val requestBody = json.toString().toRequestBody(JSON)
        val refreshRequest = Request.Builder()
            .url("$BASE_URL/api/auth/refresh-token")
            .post(requestBody)
            .build()

        client.newCall(refreshRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                originalCallback.onError(originalError)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    try {
                        val result = JSONObject(responseBody)
                        if (result.optBoolean("success", false)) {
                            prefManager.saveAuthToken(result.getString("token"))
                            prefManager.saveRefreshToken(result.getString("refreshToken"))
                            originalCallback.onError("token_refreshed")
                        } else {
                            originalCallback.onError(originalError)
                        }
                    } catch (e: Exception) {
                        originalCallback.onError(originalError)
                    }
                } else {
                    // refreshToken也过期，需要重新登录
                    prefManager.clearLoginState()
                    originalCallback.onError("session_expired")
                }
            }
        })
    }

    companion object {
        @Volatile
        private var instance: ApiClient? = null

        fun getInstance(context: Context? = null): ApiClient {
            // 已有实例直接返回
            instance?.let { return it }
            // 首次创建必须有 context
            val ctx = context ?: throw IllegalStateException("ApiClient未初始化，首次调用必须传入Context")
            return synchronized(this) {
                instance ?: ApiClient(ctx).also { instance = it }
            }
        }
    }
}
