package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TopicDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TOPIC_ID = "topic_id"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var coverImage: ImageView
    private lateinit var topicName: TextView
    private lateinit var topicDescription: TextView
    private lateinit var newsCount: TextView
    private lateinit var discussionCount: TextView
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var prefManager: PrefManager
    private lateinit var newsAdapter: NewsAdapter

    private var topicId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        topicId = intent.getStringExtra(EXTRA_TOPIC_ID)
        
        prefManager = PrefManager(this)
        
        initViews()
        setupToolbar()
        setupRecyclerView()
        loadTopicData()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        coverImage = findViewById(R.id.coverImage)
        topicName = findViewById(R.id.topicName)
        topicDescription = findViewById(R.id.topicDescription)
        newsCount = findViewById(R.id.newsCount)
        discussionCount = findViewById(R.id.discussionCount)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(mutableListOf()) { news ->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra("news_url", news.url)
            intent.putExtra("news_title", news.title)
            intent.putExtra("news_pic", news.pic)
            intent.putExtra("news_category", news.category)
            intent.putExtra("news_id", news.title.hashCode().toString())
            startActivity(intent)
        }
        
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TopicDetailActivity)
            adapter = newsAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun loadTopicData() {
        val topics = prefManager.getTopics()
        val topic = topics.find { it.id == topicId }
        
        topic?.let {
            Glide.with(this)
                .load(it.coverImage)
                .placeholder(R.drawable.ic_home)
                .into(coverImage)

            topicName.text = it.name
            topicDescription.text = it.description
            newsCount.text = "${it.newsCount} 篇文章"
            discussionCount.text = "${it.discussionCount} 条讨论"

            supportActionBar?.title = it.name

            val relatedNews = prefManager.getNewsByTopic(it.id)
            newsAdapter.clearNews()
            newsAdapter.addNews(relatedNews)
        }
    }
}
