package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var adapter: ChatMessageAdapter
    private lateinit var prefManager: PrefManager
    private val messages = mutableListOf<ChatMessage>()
    
    private lateinit var groupId: String
    private lateinit var groupName: String
    private lateinit var currentUserId: String
    private lateinit var currentUsername: String

    private val mockUsers = listOf(
        "user1" to "张三",
        "user2" to "李四",
        "user3" to "王五"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        groupId = intent.getStringExtra("group_id") ?: ""
        groupName = intent.getStringExtra("group_name") ?: "群聊"

        initViews()
        setupToolbar()
        setupRecyclerView()
        loadMessages()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        prefManager = PrefManager(this)
        
        currentUserId = prefManager.getUserId()
        currentUsername = prefManager.getUsername()
        
        btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupToolbar() {
        toolbar.title = groupName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatMessageAdapter(messages, currentUserId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadMessages() {
        val groupMessages = prefManager.getChatMessagesByGroup(groupId)
        messages.clear()
        messages.addAll(groupMessages)
        adapter.notifyDataSetChanged()
        
        if (messages.isNotEmpty()) {
            recyclerView.scrollToPosition(messages.size - 1)
        }
    }

    private fun sendMessage() {
        val content = etMessage.text.toString().trim()
        if (content.isEmpty()) return

        val messageId = UUID.randomUUID().toString()
        val currentTime = System.currentTimeMillis()
        
        val newMessage = ChatMessage(
            id = messageId,
            groupId = groupId,
            senderId = currentUserId,
            senderName = currentUsername,
            senderAvatar = "",
            content = content,
            type = ChatMessageType.TEXT,
            time = currentTime,
            isRead = false
        )
        
        prefManager.addChatMessage(newMessage)
        prefManager.updateChatGroupLastMessage(groupId, content, currentTime)
        
        messages.add(newMessage)
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
        etMessage.text.clear()

        simulateReply(content, currentTime)
    }

    private fun simulateReply(userContent: String, userTime: Long) {
        recyclerView.postDelayed({
            val randomUser = mockUsers.random()
            val replyContent = generateReply(userContent)
            val replyId = UUID.randomUUID().toString()
            val replyTime = System.currentTimeMillis()
            
            val replyMessage = ChatMessage(
                id = replyId,
                groupId = groupId,
                senderId = randomUser.first,
                senderName = randomUser.second,
                senderAvatar = "",
                content = replyContent,
                type = ChatMessageType.TEXT,
                time = replyTime,
                isRead = false
            )
            
            prefManager.addChatMessage(replyMessage)
            prefManager.updateChatGroupLastMessage(groupId, replyContent, replyTime)
            
            messages.add(replyMessage)
            adapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }, 1500)
    }

    private fun generateReply(userContent: String): String {
        val replies = listOf(
            "收到！",
            "好的，我知道了",
            "这个话题很有意思",
            "同意你的观点",
            "说得对！",
            "让我想想...",
            "确实如此",
            "我也这么觉得",
            "学习了",
            "👍"
        )
        return replies.random()
    }
}