package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class PrefManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "login_prefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_POINTS = "points"
        private const val KEY_LAST_CHECKIN_DATE = "last_checkin_date"
        private const val KEY_FOLLOWING_COUNT = "following_count"
        private const val KEY_FOLLOWERS_COUNT = "followers_count"
        private const val KEY_FRIENDS_COUNT = "friends_count"
        private const val KEY_LIKES_COUNT = "likes_count"
        private const val KEY_AVATAR_PATH = "avatar_path"
    }

    fun saveLoginState(isLoggedIn: Boolean, username: String, userId: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            putString(KEY_USERNAME, username)
            putString(KEY_USER_ID, userId)
            apply()
        }
    }

    fun clearLoginState() {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putString(KEY_USERNAME, "")
            putString(KEY_USER_ID, "")
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUsername(): String {
        return prefs.getString(KEY_USERNAME, "") ?: ""
    }

    fun getUserId(): String {
        return prefs.getString(KEY_USER_ID, "") ?: ""
    }

    fun savePoints(points: Int) {
        prefs.edit().putInt(KEY_POINTS, points).apply()
    }

    fun getPoints(): Int {
        return prefs.getInt(KEY_POINTS, 0)
    }

    fun saveCheckInDate(date: String) {
        prefs.edit().putString(KEY_LAST_CHECKIN_DATE, date).apply()
    }

    fun getLastCheckInDate(): String {
        return prefs.getString(KEY_LAST_CHECKIN_DATE, "") ?: ""
    }

    fun isCheckedInToday(today: String): Boolean {
        return prefs.getString(KEY_LAST_CHECKIN_DATE, "") == today
    }

    fun saveFollowingCount(count: Int) {
        prefs.edit().putInt(KEY_FOLLOWING_COUNT, count).apply()
    }

    fun getFollowingCount(): Int {
        return prefs.getInt(KEY_FOLLOWING_COUNT, 0)
    }

    fun saveFollowersCount(count: Int) {
        prefs.edit().putInt(KEY_FOLLOWERS_COUNT, count).apply()
    }

    fun getFollowersCount(): Int {
        return prefs.getInt(KEY_FOLLOWERS_COUNT, 0)
    }

    fun saveFriendsCount(count: Int) {
        prefs.edit().putInt(KEY_FRIENDS_COUNT, count).apply()
    }

    fun getFriendsCount(): Int {
        return prefs.getInt(KEY_FRIENDS_COUNT, 0)
    }

    fun saveLikesCount(count: Int) {
        prefs.edit().putInt(KEY_LIKES_COUNT, count).apply()
    }

    fun getLikesCount(): Int {
        return prefs.getInt(KEY_LIKES_COUNT, 0)
    }

    fun saveUserStats(following: Int, followers: Int, friends: Int, likes: Int) {
        prefs.edit().apply {
            putInt(KEY_FOLLOWING_COUNT, following)
            putInt(KEY_FOLLOWERS_COUNT, followers)
            putInt(KEY_FRIENDS_COUNT, friends)
            putInt(KEY_LIKES_COUNT, likes)
            apply()
        }
    }

    fun saveAvatarPath(path: String) {
        prefs.edit().putString(KEY_AVATAR_PATH, path).apply()
    }

    fun getAvatarPath(): String {
        return prefs.getString(KEY_AVATAR_PATH, "") ?: ""
    }

    fun clearAvatarPath() {
        prefs.edit().putString(KEY_AVATAR_PATH, "").apply()
    }
}
