package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PointsCenterActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var toolbar: Toolbar
    private lateinit var pointsTextView: TextView
    private lateinit var mallEntry: LinearLayout
    private lateinit var recordsEntry: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_center)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        prefManager = PrefManager(this)

        pointsTextView = findViewById(R.id.pointsTextView)
        mallEntry = findViewById(R.id.mallEntry)
        recordsEntry = findViewById(R.id.recordsEntry)

        loadData()
        setupListeners()
    }

    private fun loadData() {
        val points = prefManager.getPoints()
        pointsTextView.text = points.toString()
    }

    private fun setupListeners() {
        mallEntry.setOnClickListener {
            val intent = Intent(this, PointsMallActivity::class.java)
            startActivity(intent)
        }

        recordsEntry.setOnClickListener {
            val intent = Intent(this, ExchangeRecordsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
