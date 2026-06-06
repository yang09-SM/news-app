package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class MessageDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tvTitle: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvContent: TextView
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)

        initViews()
        setupToolbar()
        loadMessage()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tvTitle = findViewById(R.id.tvTitle)
        tvTime = findViewById(R.id.tvTime)
        tvContent = findViewById(R.id.tvContent)
        prefManager = PrefManager(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadMessage() {
        val messageId = intent.getStringExtra("message_id")
        if (messageId != null) {
            val message = prefManager.getMessages().find { it.id == messageId }
            if (message != null) {
                tvTitle.text = message.title
                tvTime.text = formatTime(message.time)
                tvContent.text = message.content
            }
        }
    }

    private fun formatTime(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(time))
    }
}
