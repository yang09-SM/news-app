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
}
