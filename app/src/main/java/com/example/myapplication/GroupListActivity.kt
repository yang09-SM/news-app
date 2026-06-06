package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupListActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var adapter: GroupAdapter
    private lateinit var prefManager: PrefManager
    private val groups = mutableListOf<ChatGroup>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_list)

        initViews()
        setupToolbar()
        setupRecyclerView()
        loadGroups()
    }

    override fun onResume() {
        super.onResume()
        loadGroups()
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
        adapter = GroupAdapter(
            groups,
            onItemClickListener = { group ->
                prefManager.markChatGroupAsRead(group.id)
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("group_id", group.id)
                intent.putExtra("group_name", group.name)
                startActivity(intent)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadGroups() {
        val allGroups = prefManager.getChatGroups()
        groups.clear()
        groups.addAll(allGroups)
        adapter.notifyDataSetChanged()
        
        if (groups.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}