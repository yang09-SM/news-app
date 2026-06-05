package com.example.myapplication

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val BASE_URL = "http://10.0.2.2:3000"

    interface ApiCallback {
        fun onSuccess(response: String)
        fun onError(error: String)
    }

    fun register(username: String, password: String, callback: ApiCallback) {
        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        val requestBody = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("$BASE_URL/api/register")
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
            .url("$BASE_URL/api/login")
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
            .url("$BASE_URL/api/change-password")
            .post(requestBody)
            .build()

        executeRequest(request, callback)
    }

    private fun executeRequest(request: Request, callback: ApiCallback) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "网络请求失败")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    callback.onSuccess(responseBody)
                } else {
                    callback.onError(responseBody)
                }
            }
        })
    }

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

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            return instance ?: synchronized(this) {
                instance ?: ApiClient().also { instance = it }
            }
        }
    }
}
