package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class PrefManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

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
        private const val KEY_CASH_BALANCE = "cash_balance"
        private const val KEY_DATA_MIGRATED = "data_migrated"
        private const val KEY_LAST_SYNC_TIME = "last_sync_time"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"

        private const val KEY_BROWSING_HISTORY = "browsing_history"
        private const val KEY_MESSAGES = "messages"
        private const val KEY_HOT_PUSHES = "hot_pushes"
        private const val KEY_PRODUCTS = "products"
        private const val KEY_EXCHANGE_RECORDS = "exchange_records"
        private const val KEY_ACHIEVEMENTS = "achievements"
        private const val KEY_CASH_REWARD_RECORDS = "cash_reward_records"       
        private const val KEY_CHAT_MESSAGES = "chat_messages"
        private const val KEY_CHAT_GROUPS = "chat_groups"
        private const val KEY_ACTIVITIES = "activities"
        private const val KEY_CREATIONS = "creations"
        private const val KEY_REPORTS = "reports"
        private const val KEY_DATA_INITIALIZED = "data_initialized"
        private const val KEY_FAVORITES = "favorites"
        private const val KEY_NIGHT_MODE = "night_mode"
        private const val KEY_COMMENTS = "comments"
        private const val KEY_SEARCH_HISTORY = "search_history"
        private const val KEY_HOT_SEARCH = "hot_search"
        private const val KEY_USER_INTERESTS = "user_interests"
        private const val KEY_SUBSCRIPTIONS = "subscriptions"
        private const val KEY_FOLLOW_AUTHORS = "follow_authors"
        private const val KEY_TOPICS = "topics"
        private const val KEY_DISLIKED_NEWS = "disliked_news"
        private const val KEY_CHANNELS = "channels"
        private const val KEY_AUTHORS = "authors"
        private const val KEY_SUBSCRIBED_CHANNELS = "subscribed_channels"
        private const val KEY_FOLLOWED_AUTHORS = "followed_authors"
        private const val KEY_USER_BIO = "user_bio"
        private const val KEY_NICKNAME = "user_nickname"
        private const val KEY_CONTENT_REPORTS = "content_reports"
        private const val KEY_OFFLINE_NEWS = "offline_news"
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
            putString(KEY_AUTH_TOKEN, "")
            putString(KEY_REFRESH_TOKEN, "")
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

    fun isDataMigrated(): Boolean {
        return prefs.getBoolean(KEY_DATA_MIGRATED, false)
    }

    fun setDataMigrated(migrated: Boolean) {
        prefs.edit().putBoolean(KEY_DATA_MIGRATED, migrated).apply()
    }

    fun getLastSyncTime(): Long {
        return prefs.getLong(KEY_LAST_SYNC_TIME, 0)
    }

    fun setLastSyncTime(time: Long) {
        prefs.edit().putLong(KEY_LAST_SYNC_TIME, time).apply()
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

    fun getCheckInDate(): String {
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

    fun getNickname(): String {
        return prefs.getString(KEY_NICKNAME, "") ?: ""
    }

    fun saveNickname(nickname: String) {
        prefs.edit().putString(KEY_NICKNAME, nickname).apply()
    }

    fun getUserBio(): String {
        return prefs.getString(KEY_USER_BIO, "") ?: ""
    }

    fun saveUserBio(bio: String) {
        prefs.edit().putString(KEY_USER_BIO, bio).apply()
    }
    
    fun saveContentReports(reports: List<ContentReport>) {
        val json = gson.toJson(reports)
        prefs.edit().putString(KEY_CONTENT_REPORTS, json).apply()
    }
    
    fun getContentReports(): List<ContentReport> {
        val json = prefs.getString(KEY_CONTENT_REPORTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<ContentReport>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
    
    fun addContentReport(report: ContentReport) {
        val reports = getContentReports().toMutableList()
        reports.add(0, report)
        saveContentReports(reports)
    }
    
    fun getSensitiveWords(): List<String> {
        return listOf(
            "违禁词1", "违禁词2", "敏感词1", "敏感词2", 
            "色情", "暴力", "诈骗", "赌博", "毒品", "枪支"
        )
    }
    
    fun containsSensitiveWords(text: String): Boolean {
        val sensitiveWords = getSensitiveWords()
        val lowerText = text.lowercase()
        return sensitiveWords.any { lowerText.contains(it.lowercase()) }
    }
    
    fun saveOfflineNews(news: OfflineNewsItem) {
        val offlineList = getOfflineNews().toMutableList()
        offlineList.removeAll { it.id == news.id }
        offlineList.add(0, news)
        if (offlineList.size > 50) {
            offlineList.removeAt(offlineList.size - 1)
        }
        saveOfflineNewsList(offlineList)
    }
    
    fun saveOfflineNewsList(newsList: List<OfflineNewsItem>) {
        val json = gson.toJson(newsList)
        prefs.edit().putString(KEY_OFFLINE_NEWS, json).apply()
    }
    
    fun getOfflineNews(): List<OfflineNewsItem> {
        val json = prefs.getString(KEY_OFFLINE_NEWS, null)
        return if (json != null) {
            val type = object : TypeToken<List<OfflineNewsItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
    
    fun isNewsDownloaded(newsId: String): Boolean {
        return getOfflineNews().any { it.id == newsId }
    }
    
    fun removeOfflineNews(newsId: String) {
        val offlineList = getOfflineNews().filter { it.id != newsId }
        saveOfflineNewsList(offlineList)
    }

    fun saveCashBalance(balance: Double) {
        prefs.edit().putFloat(KEY_CASH_BALANCE, balance.toFloat()).apply()      
    }

    fun getCashBalance(): Double {
        return prefs.getFloat(KEY_CASH_BALANCE, 0f).toDouble()
    }

    fun saveBrowsingHistory(history: List<BrowsingHistoryItem>) {
        val json = gson.toJson(history)
        prefs.edit().putString(KEY_BROWSING_HISTORY, json).apply()
    }

    fun getBrowsingHistory(): List<BrowsingHistoryItem> {
        val json = prefs.getString(KEY_BROWSING_HISTORY, null)
        return if (json != null) {
            val type = object : TypeToken<List<BrowsingHistoryItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addBrowsingHistory(item: BrowsingHistoryItem) {
        val history = getBrowsingHistory().toMutableList()
        history.removeAll { it.newsId == item.newsId }
        history.add(0, item)
        saveBrowsingHistory(history)
    }

    fun removeBrowsingHistory(id: String) {
        val history = getBrowsingHistory().filter { it.id != id }
        saveBrowsingHistory(history)
    }

    fun clearBrowsingHistory() {
        saveBrowsingHistory(emptyList())
    }

    fun saveMessages(messages: List<MessageItem>) {
        val json = gson.toJson(messages)
        prefs.edit().putString(KEY_MESSAGES, json).apply()
    }

    fun getMessages(): List<MessageItem> {
        val json = prefs.getString(KEY_MESSAGES, null)
        return if (json != null) {
            val type = object : TypeToken<List<MessageItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addMessage(item: MessageItem) {
        val messages = getMessages().toMutableList()
        messages.add(0, item)
        saveMessages(messages)
    }

    fun removeMessage(id: String) {
        val messages = getMessages().filter { it.id != id }
        saveMessages(messages)
    }

    fun markMessageAsRead(id: String) {
        val messages = getMessages().map {
            if (it.id == id) it.copy(isRead = true) else it
        }
        saveMessages(messages)
    }

    fun markAllMessagesAsRead() {
        val messages = getMessages().map { it.copy(isRead = true) }
        saveMessages(messages)
    }

    fun getUnreadMessageCount(): Int {
        return getMessages().count { !it.isRead }
    }

    fun saveHotPushes(pushes: List<HotPushItem>) {
        val json = gson.toJson(pushes)
        prefs.edit().putString(KEY_HOT_PUSHES, json).apply()
    }

    fun getHotPushes(): List<HotPushItem> {
        val json = prefs.getString(KEY_HOT_PUSHES, null)
        return if (json != null) {
            val type = object : TypeToken<List<HotPushItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addHotPush(item: HotPushItem) {
        val pushes = getHotPushes().toMutableList()
        pushes.add(0, item)
        saveHotPushes(pushes)
    }

    fun removeHotPush(id: String) {
        val pushes = getHotPushes().filter { it.id != id }
        saveHotPushes(pushes)
    }

    fun saveProducts(products: List<ProductItem>) {
        val json = gson.toJson(products)
        prefs.edit().putString(KEY_PRODUCTS, json).apply()
    }

    fun getProducts(): List<ProductItem> {
        val json = prefs.getString(KEY_PRODUCTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<ProductItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveExchangeRecords(records: List<ExchangeRecord>) {
        val json = gson.toJson(records)
        prefs.edit().putString(KEY_EXCHANGE_RECORDS, json).apply()
    }

    fun getExchangeRecords(): List<ExchangeRecord> {
        val json = prefs.getString(KEY_EXCHANGE_RECORDS, null)
        return if (json != null) {
            val type = object : TypeToken<List<ExchangeRecord>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addExchangeRecord(item: ExchangeRecord) {
        val records = getExchangeRecords().toMutableList()
        records.add(0, item)
        saveExchangeRecords(records)
    }

    fun saveAchievements(achievements: List<AchievementItem>) {
        val json = gson.toJson(achievements)
        prefs.edit().putString(KEY_ACHIEVEMENTS, json).apply()
    }

    fun getAchievements(): List<AchievementItem> {
        val json = prefs.getString(KEY_ACHIEVEMENTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<AchievementItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun unlockAchievement(id: String, unlockTime: Long) {
        val achievements = getAchievements().map {
            if (it.id == id) it.copy(isUnlocked = true, unlockTime = unlockTime) else it
        }
        saveAchievements(achievements)
    }

    fun saveCashRewardRecords(records: List<CashRewardRecord>) {
        val json = gson.toJson(records)
        prefs.edit().putString(KEY_CASH_REWARD_RECORDS, json).apply()
    }

    fun getCashRewardRecords(): List<CashRewardRecord> {
        val json = prefs.getString(KEY_CASH_REWARD_RECORDS, null)
        return if (json != null) {
            val type = object : TypeToken<List<CashRewardRecord>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addCashRewardRecord(item: CashRewardRecord) {
        val records = getCashRewardRecords().toMutableList()
        records.add(0, item)
        saveCashRewardRecords(records)
    }

    fun saveChatMessages(messages: List<ChatMessage>) {
        val json = gson.toJson(messages)
        prefs.edit().putString(KEY_CHAT_MESSAGES, json).apply()
    }

    fun getChatMessages(): List<ChatMessage> {
        val json = prefs.getString(KEY_CHAT_MESSAGES, null)
        return if (json != null) {
            val type = object : TypeToken<List<ChatMessage>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun getChatMessagesByGroup(groupId: String): List<ChatMessage> {      
        return getChatMessages().filter { it.groupId == groupId }
    }

    fun addChatMessage(item: ChatMessage) {
        val messages = getChatMessages().toMutableList()
        messages.add(item)
        saveChatMessages(messages)
    }

    fun saveChatGroups(groups: List<ChatGroup>) {
        val json = gson.toJson(groups)
        prefs.edit().putString(KEY_CHAT_GROUPS, json).apply()
    }

    fun getChatGroups(): List<ChatGroup> {
        val json = prefs.getString(KEY_CHAT_GROUPS, null)
        return if (json != null) {
            val type = object : TypeToken<List<ChatGroup>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addChatGroup(item: ChatGroup) {
        val groups = getChatGroups().toMutableList()
        groups.add(0, item)
        saveChatGroups(groups)
    }

    fun updateChatGroupLastMessage(groupId: String, message: String, time: Long) {
        val groups = getChatGroups().map {
            if (it.id == groupId) {
                it.copy(lastMessage = message, lastMessageTime = time, unreadCount = it.unreadCount + 1)
            } else {
                it
            }
        }
        saveChatGroups(groups)
    }

    fun markChatGroupAsRead(groupId: String) {
        val groups = getChatGroups().map {
            if (it.id == groupId) it.copy(unreadCount = 0) else it
        }
        saveChatGroups(groups)
    }

    fun saveActivities(activities: List<ActivityItem>) {
        val json = gson.toJson(activities)
        prefs.edit().putString(KEY_ACTIVITIES, json).apply()
    }

    fun getActivities(): List<ActivityItem> {
        val json = prefs.getString(KEY_ACTIVITIES, null)
        return if (json != null) {
            val type = object : TypeToken<List<ActivityItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addActivity(item: ActivityItem) {
        val activities = getActivities().toMutableList()
        activities.add(0, item)
        saveActivities(activities)
    }

    fun updateActivityRegistration(id: String, isRegistered: Boolean) {
        val activities = getActivities().map {
            if (it.id == id) it.copy(isRegistered = isRegistered) else it       
        }
        saveActivities(activities)
    }

    fun saveCreations(creations: List<CreationItem>) {
        val json = gson.toJson(creations)
        prefs.edit().putString(KEY_CREATIONS, json).apply()
    }

    fun getCreations(): List<CreationItem> {
        val json = prefs.getString(KEY_CREATIONS, null)
        return if (json != null) {
            val type = object : TypeToken<List<CreationItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addCreation(item: CreationItem) {
        val creations = getCreations().toMutableList()
        creations.add(0, item)
        saveCreations(creations)
    }

    fun updateCreation(item: CreationItem) {
        val creations = getCreations().map {
            if (it.id == item.id) item else it
        }
        saveCreations(creations)
    }

    fun deleteCreation(id: String) {
        val creations = getCreations().filter { it.id != id }
        saveCreations(creations)
    }

    fun saveReports(reports: List<ReportItem>) {
        val json = gson.toJson(reports)
        prefs.edit().putString(KEY_REPORTS, json).apply()
    }

    fun getReports(): List<ReportItem> {
        val json = prefs.getString(KEY_REPORTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<ReportItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addReport(item: ReportItem) {
        val reports = getReports().toMutableList()
        reports.add(0, item)
        saveReports(reports)
    }

    fun saveFavorites(favorites: List<FavoriteItem>) {
        val json = gson.toJson(favorites)
        prefs.edit().putString(KEY_FAVORITES, json).apply()
    }

    fun getFavorites(): List<FavoriteItem> {
        val json = prefs.getString(KEY_FAVORITES, null)
        return if (json != null) {
            val type = object : TypeToken<List<FavoriteItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addFavorite(item: FavoriteItem) {
        val favorites = getFavorites().toMutableList()
        favorites.removeAll { it.newsId == item.newsId }
        favorites.add(0, item)
        saveFavorites(favorites)
    }

    fun removeFavorite(newsId: String) {
        val favorites = getFavorites().filter { it.newsId != newsId }
        saveFavorites(favorites)
    }

    fun isFavorited(newsId: String): Boolean {
        return getFavorites().any { it.newsId == newsId }
    }

    fun clearFavorites() {
        saveFavorites(emptyList())
    }

    fun saveComments(comments: List<Comment>) {
        val json = gson.toJson(comments)
        prefs.edit().putString(KEY_COMMENTS, json).apply()
    }

    fun getComments(): List<Comment> {
        val json = prefs.getString(KEY_COMMENTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Comment>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun getCommentsByNewsId(newsId: String): List<Comment> {
        return getComments().filter { it.newsId == newsId }
    }

    fun getCommentCountByNewsId(newsId: String): Int {
        return getCommentsByNewsId(newsId).size
    }

    fun addComment(comment: Comment) {
        val comments = getComments().toMutableList()
        comments.add(0, comment)

        if (comment.parentId != null) {
            val parentIndex = comments.indexOfFirst { it.id == comment.parentId }
            if (parentIndex != -1) {
                val parent = comments[parentIndex]
                comments[parentIndex] = parent.copy(replyCount = parent.replyCount + 1)
            }
        }

        saveComments(comments)
    }

    fun toggleCommentLike(commentId: String): Comment? {
        val comments = getComments().toMutableList()
        val index = comments.indexOfFirst { it.id == commentId }
        if (index != -1) {
            val comment = comments[index]
            val newLikeCount = if (comment.isLiked) comment.likeCount - 1 else comment.likeCount + 1
            val updatedComment = comment.copy(isLiked = !comment.isLiked, likeCount = newLikeCount)
            comments[index] = updatedComment
            saveComments(comments)
            return updatedComment
        }
        return null
    }

    fun saveNightMode(mode: Int) {
        prefs.edit().putInt(KEY_NIGHT_MODE, mode).apply()
    }

    fun getNightMode(): Int {
        return prefs.getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun saveSearchHistory(history: List<String>) {
        val json = gson.toJson(history)
        prefs.edit().putString(KEY_SEARCH_HISTORY, json).apply()
    }

    fun getSearchHistory(): List<String> {
        val json = prefs.getString(KEY_SEARCH_HISTORY, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addSearchHistory(query: String) {
        val history = getSearchHistory().toMutableList()
        history.remove(query)
        history.add(0, query)
        if (history.size > 10) {
            history.removeAt(history.size - 1)
        }
        saveSearchHistory(history)
    }

    fun clearSearchHistory() {
        saveSearchHistory(emptyList())
    }

    fun saveHotSearch(hotSearch: List<String>) {
        val json = gson.toJson(hotSearch)
        prefs.edit().putString(KEY_HOT_SEARCH, json).apply()
    }

    fun getHotSearch(): List<String> {
        val json = prefs.getString(KEY_HOT_SEARCH, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveUserInterests(interests: List<String>) {
        val json = gson.toJson(interests)
        prefs.edit().putString(KEY_USER_INTERESTS, json).apply()
    }

    fun getUserInterests(): List<String> {
        val json = prefs.getString(KEY_USER_INTERESTS, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveTopics(topics: List<Topic>) {
        val json = gson.toJson(topics)
        prefs.edit().putString(KEY_TOPICS, json).apply()
    }

    fun getTopics(): List<Topic> {
        val json = prefs.getString(KEY_TOPICS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Topic>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun getNewsByTopic(topicId: String): List<NewsItem> {
        return emptyList()
    }

    fun saveChannels(channels: List<Channel>) {
        val json = gson.toJson(channels)
        prefs.edit().putString(KEY_CHANNELS, json).apply()
    }

    fun getChannels(): List<Channel> {
        val json = prefs.getString(KEY_CHANNELS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Channel>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun toggleChannelSubscription(channelId: String): List<Channel> {
        val channels = getChannels().toMutableList()
        val index = channels.indexOfFirst { it.id == channelId }
        if (index != -1) {
            channels[index] = channels[index].copy(isSubscribed = !channels[index].isSubscribed)
            saveChannels(channels)
        }
        return channels
    }

    fun getSubscribedChannels(): List<Channel> {
        return getChannels().filter { it.isSubscribed }
    }

    fun saveAuthors(authors: List<Author>) {
        val json = gson.toJson(authors)
        prefs.edit().putString(KEY_AUTHORS, json).apply()
    }

    fun getAuthors(): List<Author> {
        val json = prefs.getString(KEY_AUTHORS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Author>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun toggleAuthorFollow(authorId: String): List<Author> {
        val authors = getAuthors().toMutableList()
        val index = authors.indexOfFirst { it.id == authorId }
        if (index != -1) {
            authors[index] = authors[index].copy(isFollowed = !authors[index].isFollowed)
            saveAuthors(authors)
        }
        return authors
    }

    fun getFollowedAuthors(): List<Author> {
        return getAuthors().filter { it.isFollowed }
    }

    fun saveDislikedNews(disliked: List<String>) {
        val json = gson.toJson(disliked)
        prefs.edit().putString(KEY_DISLIKED_NEWS, json).apply()
    }

    fun getDislikedNews(): List<String> {
        val json = prefs.getString(KEY_DISLIKED_NEWS, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addDislikedNews(newsId: String) {
        val disliked = getDislikedNews().toMutableList()
        if (!disliked.contains(newsId)) {
            disliked.add(newsId)
            saveDislikedNews(disliked)
        }
    }

    fun initializeMockData() {
        val needInitialize = !prefs.getBoolean(KEY_DATA_INITIALIZED, false) || 
                           getChannels().isEmpty() || 
                           getAuthors().isEmpty() || 
                           getTopics().isEmpty()

        if (!needInitialize) {
            return
        }

        val topics = listOf(
            Topic("1", "科技前沿", "探索最新科技动态", "https://example.com/tech.jpg", 128, 56),
            Topic("2", "财经资讯", "深度解读财经新闻", "https://example.com/finance.jpg", 96, 42),
            Topic("3", "体育世界", "精彩赛事直播", "https://example.com/sports.jpg", 256, 89),
            Topic("4", "娱乐八卦", "明星资讯速递", "https://example.com/entertainment.jpg", 312, 124)
        )
        saveTopics(topics)

        val channels = listOf(
            Channel("1", "头条新闻", "https://example.com/channel1.jpg", "最新资讯第一时间送达", 1280, true),
            Channel("2", "科技频道", "https://example.com/channel2.jpg", "探索科技前沿", 856, false),
            Channel("3", "财经频道", "https://example.com/channel3.jpg", "深度财经分析", 623, true),
            Channel("4", "体育频道", "https://example.com/channel4.jpg", "精彩体育赛事", 945, false),
            Channel("5", "娱乐频道", "https://example.com/channel5.jpg", "明星娱乐资讯", 1567, true)
        )
        saveChannels(channels)

        val authors = listOf(
            Author("1", "张记者", "https://example.com/author1.jpg", "资深财经记者，专注行业分析", 1258, 456, true),
            Author("2", "李编辑", "https://example.com/author2.jpg", "科技领域专家", 892, 234, false),
            Author("3", "王评论员", "https://example.com/author3.jpg", "体育评论员", 2345, 678, true),
            Author("4", "赵作家", "https://example.com/author4.jpg", "文化专栏作家", 567, 123, false)
        )
        saveAuthors(authors)

        saveDislikedNews(emptyList())

        prefs.edit().putBoolean(KEY_DATA_INITIALIZED, true).apply()
    }

    fun getAllDataForMigration(): JSONObject {
        val data = JSONObject()
        
        data.put("points", getPoints())
        data.put("cashBalance", getCashBalance())
        data.put("nickname", getNickname())
        data.put("userBio", getUserBio())
        data.put("avatarPath", getAvatarPath())
        data.put("lastCheckinDate", getCheckInDate())
        
        data.put("browsingHistory", gson.toJson(getBrowsingHistory()))
        data.put("favorites", gson.toJson(getFavorites()))
        data.put("comments", gson.toJson(getComments()))
        data.put("offlineNews", gson.toJson(getOfflineNews()))
        data.put("achievements", gson.toJson(getAchievements()))
        data.put("activities", gson.toJson(getActivities()))
        data.put("creations", gson.toJson(getCreations()))
        data.put("reports", gson.toJson(getReports()))
        data.put("messages", gson.toJson(getMessages()))
        data.put("chatMessages", gson.toJson(getChatMessages()))
        data.put("chatGroups", gson.toJson(getChatGroups()))
        data.put("exchangeRecords", gson.toJson(getExchangeRecords()))
        data.put("cashRewardRecords", gson.toJson(getCashRewardRecords()))
        data.put("searchHistory", gson.toJson(getSearchHistory()))
        data.put("userInterests", gson.toJson(getUserInterests()))
        data.put("subscribedChannels", gson.toJson(getSubscribedChannels()))
        data.put("followedAuthors", gson.toJson(getFollowedAuthors()))
        data.put("dislikedNews", gson.toJson(getDislikedNews()))
        
        return data
    }

    fun loadDataFromCloud(data: JSONObject) {
        if (data.has("points")) {
            savePoints(data.getInt("points"))
        }
        if (data.has("cashBalance")) {
            saveCashBalance(data.getDouble("cashBalance"))
        }
        if (data.has("nickname")) {
            saveNickname(data.getString("nickname"))
        }
        if (data.has("userBio")) {
            saveUserBio(data.getString("userBio"))
        }
        if (data.has("avatarPath")) {
            saveAvatarPath(data.getString("avatarPath"))
        }
        if (data.has("lastCheckinDate")) {
            saveCheckInDate(data.getString("lastCheckinDate"))
        }
        
        if (data.has("browsingHistory")) {
            val type = object : TypeToken<List<BrowsingHistoryItem>>() {}.type
            saveBrowsingHistory(gson.fromJson(data.getString("browsingHistory"), type))
        }
        if (data.has("favorites")) {
            val type = object : TypeToken<List<FavoriteItem>>() {}.type
            saveFavorites(gson.fromJson(data.getString("favorites"), type))
        }
        if (data.has("comments")) {
            val type = object : TypeToken<List<Comment>>() {}.type
            saveComments(gson.fromJson(data.getString("comments"), type))
        }
        if (data.has("offlineNews")) {
            val type = object : TypeToken<List<OfflineNewsItem>>() {}.type
            saveOfflineNewsList(gson.fromJson(data.getString("offlineNews"), type))
        }
        if (data.has("achievements")) {
            val type = object : TypeToken<List<AchievementItem>>() {}.type
            saveAchievements(gson.fromJson(data.getString("achievements"), type))
        }
        if (data.has("activities")) {
            val type = object : TypeToken<List<ActivityItem>>() {}.type
            saveActivities(gson.fromJson(data.getString("activities"), type))
        }
        if (data.has("creations")) {
            val type = object : TypeToken<List<CreationItem>>() {}.type
            saveCreations(gson.fromJson(data.getString("creations"), type))
        }
        if (data.has("reports")) {
            val type = object : TypeToken<List<ReportItem>>() {}.type
            saveReports(gson.fromJson(data.getString("reports"), type))
        }
        if (data.has("messages")) {
            val type = object : TypeToken<List<MessageItem>>() {}.type
            saveMessages(gson.fromJson(data.getString("messages"), type))
        }
        if (data.has("chatMessages")) {
            val type = object : TypeToken<List<ChatMessage>>() {}.type
            saveChatMessages(gson.fromJson(data.getString("chatMessages"), type))
        }
        if (data.has("chatGroups")) {
            val type = object : TypeToken<List<ChatGroup>>() {}.type
            saveChatGroups(gson.fromJson(data.getString("chatGroups"), type))
        }
        if (data.has("exchangeRecords")) {
            val type = object : TypeToken<List<ExchangeRecord>>() {}.type
            saveExchangeRecords(gson.fromJson(data.getString("exchangeRecords"), type))
        }
        if (data.has("cashRewardRecords")) {
            val type = object : TypeToken<List<CashRewardRecord>>() {}.type
            saveCashRewardRecords(gson.fromJson(data.getString("cashRewardRecords"), type))
        }
        if (data.has("searchHistory")) {
            val type = object : TypeToken<List<String>>() {}.type
            saveSearchHistory(gson.fromJson(data.getString("searchHistory"), type))
        }
        if (data.has("userInterests")) {
            val type = object : TypeToken<List<String>>() {}.type
            saveUserInterests(gson.fromJson(data.getString("userInterests"), type))
        }
        
        setLastSyncTime(System.currentTimeMillis())
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String {
        return prefs.getString(KEY_AUTH_TOKEN, "") ?: ""
    }

    fun saveRefreshToken(token: String) {
        prefs.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

    fun getRefreshToken(): String {
        return prefs.getString(KEY_REFRESH_TOKEN, "") ?: ""
    }

    fun hasToken(): Boolean {
        return getAuthToken().isNotEmpty()
    }
}
