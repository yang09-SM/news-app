package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExchangeRecordsActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: ExchangeRecordAdapter
    private val recordList = mutableListOf<ExchangeRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_records)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        prefManager = PrefManager(this)

        initViews()
        loadRecords()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recordsRecyclerView)
        emptyView = findViewById(R.id.emptyView)

        adapter = ExchangeRecordAdapter(recordList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExchangeRecordsActivity)
            adapter = this@ExchangeRecordsActivity.adapter
        }
    }

    private fun loadRecords() {
        val records = prefManager.getExchangeRecords()
        recordList.clear()
        recordList.addAll(records)
        updateUI()
    }

    private fun updateUI() {
        if (recordList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }
}
