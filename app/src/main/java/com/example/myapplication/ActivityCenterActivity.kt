package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityCenterActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: ActivityAdapter
    private val activityList = mutableListOf<ActivityItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_center)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        prefManager = PrefManager(this)

        initViews()
        loadData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.activitiesRecyclerView)
        emptyView = findViewById(R.id.emptyView)

        adapter = ActivityAdapter(
            activityList,
            onActivityClickListener = { activityItem ->
                val intent = Intent(this, ActivityDetailActivity::class.java)
                intent.putExtra("activityId", activityItem.id)
                startActivity(intent)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityCenterActivity)
            adapter = this@ActivityCenterActivity.adapter
        }
    }

    private fun loadData() {
        val activities = prefManager.getActivities()
        activityList.clear()
        activityList.addAll(activities)
        updateUI()
    }

    private fun updateUI() {
        if (activityList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}