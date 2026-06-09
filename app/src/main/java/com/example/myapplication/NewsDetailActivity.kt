package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager
    private var isFavorited = false

    private lateinit var currentNewsId: String
    private lateinit var currentNewsTitle: String
    private lateinit var currentNewsPic: String
    private lateinit var currentNewsCategory: String
    private lateinit var currentNewsUrl: String

    private lateinit var commentsHeader: LinearLayout
    private lateinit var commentsSection: LinearLayout
    private lateinit var commentsArrow: ImageView
    private lateinit var commentCount: TextView
    private lateinit var commentsRecyclerView: RecyclerView
    private lateinit var commentInput: EditText
    private lateinit var sendCommentBtn: Button
    private lateinit var tabHot: TextView
    private lateinit var tabLatest: TextView

    private lateinit var commentAdapter: CommentAdapter
    private var commentsExpanded = false
    private var isHotTab = true
    private var replyingTo: Comment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        currentNewsUrl = intent.getStringExtra("news_url") ?: ""
        currentNewsTitle = intent.getStringExtra("news_title") ?: ""
        currentNewsPic = intent.getStringExtra("news_pic") ?: ""
        currentNewsCategory = intent.getStringExtra("news_category") ?: "新闻"
        currentNewsId = intent.getStringExtra("news_id") ?: UUID.randomUUID().toString()

        supportActionBar?.title = currentNewsTitle

        isFavorited = prefManager.isFavorited(currentNewsId)

        setupWebView()

        if (currentNewsUrl.isNotEmpty()) {
            webView.loadUrl(currentNewsUrl)
        }

        saveBrowsingHistory(currentNewsId, currentNewsTitle, currentNewsPic, currentNewsCategory, currentNewsUrl)

        initCommentViews()
        loadComments()
    }

    /**
     * 配置WebView - 优化富媒体渲染和阅读体验
     */
    private fun setupWebView() {
        webView = findViewById(R.id.webview)
        val settings = webView.settings

        // 基本设置
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true

        // 富媒体支持
        settings.mediaPlaybackRequiresUserGesture = false
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.allowFileAccess = true
        settings.allowContentAccess = true

        // 文字大小和排版优化
        settings.textZoom = 100
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING

        // 缓存策略 - 优先使用缓存提升加载速度
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCacheEnabled(true)

        // 夜间模式支持
        val nightMode = prefManager.getNightMode()
        if (nightMode == androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES) {
            webView.settings.setForceDark(WebSettings.FORCE_DARK_ON)
        }

        // WebViewClient - 处理页面跳转
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                // 外部链接用浏览器打开
                if (url != currentNewsUrl && !url.startsWith("file:///")) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url))
                    startActivity(browserIntent)
                    return true
                }
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 注入CSS优化阅读体验
                injectReadabilityCSS()
            }
        }

        // WebChromeClient - 支持视频全屏等
        webView.webChromeClient = WebChromeClient()
    }

    /**
     * 注入CSS优化阅读体验
     */
    private fun injectReadabilityCSS() {
        val nightMode = prefManager.getNightMode()
        val css = if (nightMode == androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES) {
            """
            var style = document.createElement('style');
            style.innerHTML = '
                body { color: #e0e0e0 !important; background-color: #1a1a1a !important; }
                p, div, span, li, td, th { color: #d0d0d0 !important; }
                a { color: #64b5f6 !important; }
                img { max-width: 100% !important; height: auto !important; border-radius: 8px !important; }
                video { max-width: 100% !important; }
                h1, h2, h3 { color: #ffffff !important; }
            ';
            document.head.appendChild(style);
            """
        } else {
            """
            var style = document.createElement('style');
            style.innerHTML = '
                body { line-height: 1.8 !important; font-size: 16px !important; padding: 0 8px !important; }
                img { max-width: 100% !important; height: auto !important; border-radius: 8px !important; margin: 8px 0 !important; }
                video { max-width: 100% !important; }
                p { margin: 12px 0 !important; }
            ';
            document.head.appendChild(style);
            """
        }
        webView.evaluateJavascript(css, null)
    }

    private fun initCommentViews() {
        commentsHeader = findViewById(R.id.commentsHeader)
        commentsSection = findViewById(R.id.commentsSection)
        commentsArrow = findViewById(R.id.commentsArrow)
        commentCount = findViewById(R.id.commentCount)
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)
        commentInput = findViewById(R.id.commentInput)
        sendCommentBtn = findViewById(R.id.sendCommentBtn)
        tabHot = findViewById(R.id.tabHot)
        tabLatest = findViewById(R.id.tabLatest)

        commentsRecyclerView.layoutManager = LinearLayoutManager(this)

        commentInput.clearFocus()

        commentInput.setOnClickListener {
            commentInput.isFocusable = true
            commentInput.isFocusableInTouchMode = true
            commentInput.requestFocus()
        }

        commentsHeader.setOnClickListener {
            toggleCommentsSection()
        }

        sendCommentBtn.setOnClickListener {
            sendComment()
        }

        tabHot.setOnClickListener {
            switchTab(true)
        }

        tabLatest.setOnClickListener {
            switchTab(false)
        }
    }

    private fun loadComments() {
        val allComments = prefManager.getCommentsByNewsId(currentNewsId)
        val topLevelComments = allComments.filter { it.parentId == null }.toMutableList()

        commentCount.text = allComments.size.toString()

        commentAdapter = CommentAdapter(
            this,
            topLevelComments,
            allComments,
            onLikeClick = { comment ->
                val updatedComment = prefManager.toggleCommentLike(comment.id)
                updatedComment?.let {
                    commentAdapter.updateComment(it)
                    if (comment.parentId != null) {
                        loadComments()
                    }
                }
            },
            onReplyClick = { comment ->
                replyingTo = comment
                commentInput.hint = "回复 ${comment.userName}"
                commentInput.requestFocus()
            },
            onReportClick = { comment ->
                showReportDialog(comment)
            }
        )

        commentsRecyclerView.adapter = commentAdapter
    }

    private fun toggleCommentsSection() {
        commentsExpanded = !commentsExpanded
        if (commentsExpanded) {
            commentsSection.visibility = View.VISIBLE
            commentsArrow.setImageResource(android.R.drawable.arrow_up_float)
        } else {
            commentsSection.visibility = View.GONE
            commentsArrow.setImageResource(android.R.drawable.arrow_down_float)
        }
    }

    private fun switchTab(hot: Boolean) {
        isHotTab = hot

        if (hot) {
            tabHot.setBackgroundResource(R.drawable.tab_selected_background)
            tabHot.setTextColor(ContextCompat.getColor(this, R.color.primary_color))
            tabLatest.background = null
            tabLatest.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        } else {
            tabLatest.setBackgroundResource(R.drawable.tab_selected_background)
            tabLatest.setTextColor(ContextCompat.getColor(this, R.color.primary_color))
            tabHot.background = null
            tabHot.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        loadComments()
    }

    private fun sendComment() {
        val content = commentInput.text.toString().trim()
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show()
            return
        }

        if (prefManager.containsSensitiveWords(content)) {
            Toast.makeText(this, "评论包含敏感词，请修改后重试", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = prefManager.getUserId()
        val userName = prefManager.getUsername()

        val comment = Comment(
            id = UUID.randomUUID().toString(),
            newsId = currentNewsId,
            userId = userId,
            userName = if (userName.isEmpty()) "用户${userId.take(4)}" else userName,
            userAvatar = "https://picsum.photos/seed/${userId}/100/100",
            content = content,
            parentId = replyingTo?.id,
            replyToUserName = replyingTo?.userName,
            likeCount = 0,
            isLiked = false,
            createTime = System.currentTimeMillis(),
            replyCount = 0
        )

        prefManager.addComment(comment)
        commentAdapter.addComment(comment)
        commentCount.text = prefManager.getCommentCountByNewsId(currentNewsId).toString()

        // 同步评论到服务端
        if (prefManager.isLoggedIn() && currentNewsId.toLongOrNull() != null) {
            ApiClient.getInstance(this).addComment(
                currentNewsId.toLong(),
                userId.toLongOrNull() ?: 0,
                if (userName.isEmpty()) "用户${userId.take(4)}" else userName,
                content,
                object : ApiClient.ApiCallback {
                    override fun onSuccess(response: String) {}
                    override fun onError(error: String) {}
                }
            )
        }

        commentInput.text.clear()
        replyingTo = null
        commentInput.hint = "写评论..."

        Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show()

        loadComments()
    }

    private fun showReportDialog(comment: Comment? = null) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_report, null)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val reasonGroup = dialogView.findViewById<android.widget.RadioGroup>(R.id.reportReasonGroup)
        val description = dialogView.findViewById<android.widget.EditText>(R.id.reportDescription)
        val cancelBtn = dialogView.findViewById<android.widget.Button>(R.id.cancelReport)
        val submitBtn = dialogView.findViewById<android.widget.Button>(R.id.submitReport)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        submitBtn.setOnClickListener {
            val selectedReasonId = reasonGroup.checkedRadioButtonId
            if (selectedReasonId == -1) {
                Toast.makeText(this, "请选择举报原因", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reason = when (selectedReasonId) {
                R.id.reasonFake -> "虚假信息"
                R.id.reasonPorn -> "色情低俗"
                R.id.reasonViolent -> "暴力血腥"
                R.id.reasonIllegal -> "违法内容"
                R.id.reasonOther -> "其他"
                else -> "其他"
            }

            val report = ContentReport(
                id = UUID.randomUUID().toString(),
                newsId = currentNewsId,
                newsTitle = currentNewsTitle,
                commentId = comment?.id,
                reason = reason,
                description = description.text.toString().trim(),
                reporterId = prefManager.getUserId()
            )

            prefManager.addContentReport(report)

            Toast.makeText(this, "举报已提交，我们会尽快处理", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_detail_menu, menu)
        updateFavoriteIcon(menu?.findItem(R.id.action_favorite))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                toggleFavorite()
                return true
            }
            R.id.action_push_notify -> {
                NotificationHelper(this).requestNotificationPermission(this)

                val alarmManager = getSystemService(ALARM_SERVICE) as android.app.AlarmManager
                val intent = Intent(this, PushReceiver::class.java).apply {
                    action = "com.example.myapplication.ACTION_NEWS_PUSH"
                    putExtra("push_title", "突发新闻")
                    putExtra("push_content", currentNewsTitle.ifEmpty { "重要新闻更新" })
                    putExtra("push_url", currentNewsUrl)
                    putExtra("push_news_title", currentNewsTitle)
                }
                val triggerTime = System.currentTimeMillis() + 30_000L
                val pendingIntent = android.app.PendingIntent.getBroadcast(
                    this,
                    2001,
                    intent,
                    android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
                )

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        android.app.AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    alarmManager.set(
                        android.app.AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }

                Toast.makeText(this, "将在30秒后收到推送提醒", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_share -> {
                val shareText = "${currentNewsTitle}\n${currentNewsUrl}"
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                startActivity(Intent.createChooser(shareIntent, "分享新闻到"))
                return true
            }
            R.id.action_report -> {
                showReportDialog()
                return true
            }
            R.id.action_download -> {
                downloadNews()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleFavorite() {
        val userId = prefManager.getUserId().toLongOrNull()
        val articleId = currentNewsId.toLongOrNull()

        if (isFavorited) {
            prefManager.removeFavorite(currentNewsId)
            isFavorited = false
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show()
            // 同步到服务端
            if (userId != null && articleId != null && prefManager.isLoggedIn()) {
                ApiClient.getInstance(this).removeFavorite(articleId, userId, object : ApiClient.ApiCallback {
                    override fun onSuccess(response: String) {}
                    override fun onError(error: String) {}
                })
            }
        } else {
            val favoriteItem = FavoriteItem(
                id = UUID.randomUUID().toString(),
                newsId = currentNewsId,
                title = currentNewsTitle,
                pic = currentNewsPic,
                category = currentNewsCategory,
                url = currentNewsUrl
            )
            prefManager.addFavorite(favoriteItem)
            isFavorited = true
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show()
            // 同步到服务端
            if (userId != null && articleId != null && prefManager.isLoggedIn()) {
                ApiClient.getInstance(this).addFavorite(articleId, userId, object : ApiClient.ApiCallback {
                    override fun onSuccess(response: String) {}
                    override fun onError(error: String) {}
                })
            }
        }
        invalidateOptionsMenu()
    }

    private fun updateFavoriteIcon(menuItem: MenuItem?) {
        menuItem?.let {
            if (isFavorited) {
                it.icon = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)
                it.title = "已收藏"
            } else {
                it.icon = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
                it.title = "收藏"
            }
        }
    }

    private fun saveBrowsingHistory(newsId: String, title: String, pic: String, category: String, url: String) {
        val historyItem = BrowsingHistoryItem(
            id = UUID.randomUUID().toString(),
            newsId = newsId,
            title = title,
            pic = pic,
            category = category,
            url = url,
            browseTime = System.currentTimeMillis(),
            readDuration = 0
        )
        prefManager.addBrowsingHistory(historyItem)

        // 同步浏览记录到服务端
        val userId = prefManager.getUserId().toLongOrNull()
        val articleId = newsId.toLongOrNull()
        if (userId != null && articleId != null && prefManager.isLoggedIn()) {
            ApiClient.getInstance(this).addBrowsingHistory(userId, articleId, object : ApiClient.ApiCallback {
                override fun onSuccess(response: String) {}
                override fun onError(error: String) {}
            })
            // 同时记录浏览量
            ApiClient.getInstance(this).incrementViewCount(articleId, object : ApiClient.ApiCallback {
                override fun onSuccess(response: String) {}
                override fun onError(error: String) {}
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun downloadNews() {
        if (prefManager.isNewsDownloaded(currentNewsId)) {
            Toast.makeText(this, "该新闻已下载", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "开始下载...", Toast.LENGTH_SHORT).show()

        webView.evaluateJavascript(
            "(function() { return document.documentElement.outerHTML; })();"
        ) { htmlContent ->
            val content = htmlContent?.removeSurrounding("\"")?.replace("\\u003C", "<")?.replace("\\u003E", ">")

            val offlineNews = OfflineNewsItem(
                id = currentNewsId,
                title = currentNewsTitle,
                pic = currentNewsPic,
                category = currentNewsCategory,
                url = currentNewsUrl,
                content = content
            )

            prefManager.saveOfflineNews(offlineNews)
            Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
