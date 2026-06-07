package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    fun isDataInitialized(): Boolean {
        return prefs.getBoolean(KEY_DATA_INITIALIZED, false)
    }

    fun saveNightMode(mode: Int) {
        prefs.edit().putInt(KEY_NIGHT_MODE, mode).apply()
    }

    fun getNightMode(): Int {
        return prefs.getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun initializeMockData() {
        if (isDataInitialized()) return
        
        val currentTime = System.currentTimeMillis()
        
        val browsingHistory = listOf(
            BrowsingHistoryItem("1", "n1", "人工智能时代来临", "https://picsum.photos/seed/ai-news/240/180", "科技", "https://example.com/news1", currentTime - 3600000, 120),
            BrowsingHistoryItem("2", "n2", "2024年经济发展报告", "https://picsum.photos/seed/finance-report/240/180", "财经", "https://example.com/news2", currentTime - 7200000, 180),
            BrowsingHistoryItem("3", "n3", "世界杯精彩回顾", "https://picsum.photos/seed/world-cup-sports/240/180", "体育", "https://example.com/news3", currentTime - 10800000, 90)
        )
        saveBrowsingHistory(browsingHistory)
        
        val messages = listOf(
            MessageItem("1", MessageType.SYSTEM, "系统通知", "欢迎使用新闻App！", currentTime - 86400000, true),
            MessageItem("2", MessageType.LIKE, "点赞通知", "用户张三点赞了你的评论", currentTime - 3600000, false),
            MessageItem("3", MessageType.COMMENT, "评论通知", "用户李四评论了你的文章", currentTime - 7200000, false)
        )
        saveMessages(messages)
        
        val hotPushes = listOf(
            HotPushItem("1", "重大新闻：2024科技峰会圆满落幕", "今日在京举办的全球科技峰会圆满落幕，多位行业领袖分享了最新科技成果与未来展望", "https://picsum.photos/seed/tech-summit/240/180", currentTime - 3600000, true, 15680, 2345, 856, "https://example.com/news1"),
            HotPushItem("2", "人工智能新突破：语言模型再创新高", "最新研究显示，新一代语言模型在多项测试中取得显著进步，性能提升超过30%", "https://picsum.photos/seed/ai-breakthrough/240/180", currentTime - 7200000, false, 12450, 1890, 623, "https://example.com/news2"),
            HotPushItem("3", "财经观察：股市迎来新一轮上涨", "分析师表示，近期市场信心回升，多家蓝筹股表现强劲，预计未来仍有上涨空间", "https://picsum.photos/seed/stock-market/240/180", currentTime - 10800000, false, 8920, 1456, 421, "https://example.com/news3"),
            HotPushItem("4", "体育赛事：世界杯预选赛精彩回顾", "昨晚进行的世界杯预选赛中，多支球队展现出色状态，精彩进球不断", "https://picsum.photos/seed/world-cup-match/240/180", currentTime - 14400000, false, 21340, 3456, 1023, "https://example.com/news4"),
            HotPushItem("5", "生活资讯：健康饮食新指南发布", "最新健康饮食指南建议增加蔬果摄入，减少加工食品，助力全民健康生活", "https://picsum.photos/seed/healthy-food/240/180", currentTime - 18000000, false, 7680, 987, 345, "https://example.com/news5")
        )
        saveHotPushes(hotPushes)
        
        val products = listOf(
            ProductItem("1", "精美水杯", "高品质不锈钢水杯，保温效果好", "https://picsum.photos/seed/water-cup/240/240", 500, 100, "日用品"),
            ProductItem("2", "蓝牙耳机", "无线蓝牙耳机，音质清晰", "https://picsum.photos/seed/bluetooth-earphone/240/240", 2000, 50, "数码"),
            ProductItem("3", "笔记本", "精美笔记本，书写流畅", "https://picsum.photos/seed/notebook/240/240", 200, 200, "文具")
        )
        saveProducts(products)
        
        val exchangeRecords = listOf(
            ExchangeRecord("1", "1", "精美水杯", "https://picsum.photos/seed/water-cup/200/200", 500, currentTime - 86400000, ExchangeStatus.COMPLETED)
        )
        saveExchangeRecords(exchangeRecords)
        
        val achievements = listOf(
            AchievementItem("1", "新手入门", "首次登录App", "🏆", 100, true, currentTime - 86400000, 1, 1),
            AchievementItem("2", "阅读达人", "累计阅读30篇文章", "📚", 500, false, null, 12, 30),
            AchievementItem("3", "分享使者", "分享10篇文章", "🔗", 300, false, null, 3, 10),
            AchievementItem("4", "早起鸟", "连续7天在8点前阅读", "🌅", 400, false, null, 2, 7),
            AchievementItem("5", "夜猫子", "阅读超过50篇文章", "🦉", 600, false, null, 25, 50),
            AchievementItem("6", "社交达人", "点赞20次", "👍", 200, false, null, 8, 20),
            AchievementItem("7", "收藏家", "收藏15篇文章", "⭐", 350, false, null, 5, 15),
            AchievementItem("8", "评论达人", "发表10条评论", "💬", 250, false, null, 0, 10),
            AchievementItem("9", "探索者", "浏览全部新闻分类", "🔍", 450, false, null, 3, 6),
            AchievementItem("10", "成就大师", "解锁5个成就", "👑", 800, false, null, 1, 5)
        )
        saveAchievements(achievements)
        
        val cashRewardRecords = listOf(
            CashRewardRecord("1", 0.5, RewardType.READ, "阅读文章奖励", currentTime - 3600000, RewardStatus.SUCCESS),
            CashRewardRecord("2", 0.3, RewardType.SHARE, "分享文章奖励", currentTime - 7200000, RewardStatus.SUCCESS)
        )
        saveCashRewardRecords(cashRewardRecords)
        saveCashBalance(0.8)
        
        val chatGroups = listOf(
            ChatGroup("1", "新闻讨论群", "https://picsum.photos/seed/news-discussion/96/96", 128, "今天的新闻很精彩", currentTime - 3600000, 3),
            ChatGroup("2", "科技爱好者", "https://picsum.photos/seed/tech-lovers/96/96", 256, "人工智能发展迅速", currentTime - 7200000, 0)
        )
        saveChatGroups(chatGroups)
        
        val chatMessages = listOf(
            ChatMessage("1", "1", "user1", "张三", "https://picsum.photos/seed/user-zhangsan/80/80", "今天的新闻很精彩", ChatMessageType.TEXT, currentTime - 3600000, false)
        )
        saveChatMessages(chatMessages)
        
        val activities = listOf(
            ActivityItem("1", "线下见面会", "邀请您参加我们的线下用户见面会，共同探讨新闻行业的发展趋势和未来展望。活动将包含主题分享、互动交流和茶歇时间。", "https://picsum.photos/seed/meetup/800/400", currentTime + 86400000, currentTime + 172800000, "北京市朝阳区", 50, ActivityStatus.UPCOMING, false),
            ActivityItem("2", "技术分享会", "由资深技术专家分享移动应用开发的前沿技术，包括Kotlin、Android架构、性能优化等主题。", "https://picsum.photos/seed/tech/800/400", currentTime + 259200000, currentTime + 345600000, "上海市浦东新区", 80, ActivityStatus.UPCOMING, false),
            ActivityItem("3", "产品设计研讨会", "探讨用户体验设计的最新趋势，分享优秀产品案例，学习设计思维方法。", "https://picsum.photos/seed/design/800/400", currentTime - 432000000, currentTime - 345600000, "深圳市南山区", 60, ActivityStatus.ENDED, false),
            ActivityItem("4", "社区志愿者活动", "参与社区公益活动，传递正能量，让我们一起为社会贡献一份力量。", "https://picsum.photos/seed/volunteer/800/400", currentTime + 432000000, currentTime + 518400000, "广州市天河区", 100, ActivityStatus.UPCOMING, false)
        )
        saveActivities(activities)
        
        val creations = listOf(
            CreationItem("1", "我的第一篇文章", "这是我创作的第一篇文章...", emptyList(), "生活", currentTime - 86400000, 100, 10, 2, CreationStatus.PUBLISHED)
        )
        saveCreations(creations)
        
        val reports = listOf(
            ReportItem("1", "社区环境问题", "小区附近有垃圾堆积，希望能处理", emptyList(), "某某街道", "环境", currentTime - 86400000, ReportStatus.REVIEWING, null)
        )
        saveReports(reports)
        
        prefs.edit().putBoolean(KEY_DATA_INITIALIZED, true).apply()
    }
}
