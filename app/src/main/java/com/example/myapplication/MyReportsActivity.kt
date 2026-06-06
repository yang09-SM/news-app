package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyReportsActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var submitButton: Button
    private lateinit var adapter: ReportAdapter
    private lateinit var toolbar: Toolbar
    private val reportsList = mutableListOf<ReportItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reports)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
        loadReports()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.reportsRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        submitButton = findViewById(R.id.submitReportButton)

        adapter = ReportAdapter(
            reportsList,
            onItemClickListener = { report ->
                // 可以在这里添加点击报料项后的操作，比如查看详情
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyReportsActivity)
            adapter = this@MyReportsActivity.adapter
        }

        submitButton.setOnClickListener {
            val intent = Intent(this, SubmitReportActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadReports() {
        val reports = prefManager.getReports()
        reportsList.clear()
        reportsList.addAll(reports)
        updateUI()
    }

    private fun updateUI() {
        if (reportsList.isEmpty()) {
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
        loadReports()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
