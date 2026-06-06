package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageListActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var adapter: MessageAdapter
    private lateinit var prefManager: PrefManager
    private val messages = mutableListOf<MessageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        initViews()
        setupToolbar()
        setupRecyclerView()
        loadMessages()
    }

    override fun onResume() {
        super.onResume()
        loadMessages()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        emptyState = findViewById(R.id.emptyState)
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

    private fun setupRecyclerView() {
        adapter = MessageAdapter(
            messages,
            onItemClickListener = { message ->
                prefManager.markMessageAsRead(message.id)
                val intent = Intent(this, MessageDetailActivity::class.java)
                intent.putExtra("message_id", message.id)
                startActivity(intent)
            },
            onMoreClickListener = { message, view ->
                showOptionsDialog(message)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadMessages() {
        val allMessages = prefManager.getMessages()
        messages.clear()
        messages.addAll(allMessages)
        adapter.notifyDataSetChanged()
        
        if (messages.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showOptionsDialog(message: MessageItem) {
        val options = arrayOf(
            if (message.isRead) "标记为未读" else "标记为已读",
            "删除消息"
        )
        
        AlertDialog.Builder(this)
            .setTitle(message.title)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> toggleReadStatus(message)
                    1 -> deleteMessage(message)
                }
            }
            .show()
    }

    private fun toggleReadStatus(message: MessageItem) {
        val updatedMessage = message.copy(isRead = !message.isRead)
        prefManager.saveMessages(
            prefManager.getMessages().map { 
                if (it.id == message.id) updatedMessage else it 
            }
        )
        adapter.updateMessage(updatedMessage)
    }

    private fun deleteMessage(message: MessageItem) {
        AlertDialog.Builder(this)
            .setTitle("确认删除")
            .setMessage("确定要删除这条消息吗？")
            .setPositiveButton("删除") { _, _ ->
                prefManager.removeMessage(message.id)
                adapter.removeMessage(message.id)
                if (messages.isEmpty()) {
                    emptyState.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
