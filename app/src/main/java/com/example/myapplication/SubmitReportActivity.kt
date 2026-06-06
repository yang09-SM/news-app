package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SubmitReportActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var toolbar: Toolbar
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var submitButton: Button

    private val categories = listOf("环境", "交通", "治安", "设施", "其他")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_report)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
    }

    private fun initViews() {
        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        locationEditText = findViewById(R.id.locationEditText)
        categorySpinner = findViewById(R.id.categorySpinner)
        submitButton = findViewById(R.id.submitButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        submitButton.setOnClickListener {
            submitReport()
        }
    }

    private fun submitReport() {
        val title = titleEditText.text.toString().trim()
        val content = contentEditText.text.toString().trim()
        val location = locationEditText.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "请输入报料标题", Toast.LENGTH_SHORT).show()
            return
        }

        if (content.isEmpty()) {
            Toast.makeText(this, "请输入报料内容", Toast.LENGTH_SHORT).show()
            return
        }

        val reportId = System.currentTimeMillis().toString()
        val report = ReportItem(
            id = reportId,
            title = title,
            content = content,
            images = emptyList(),
            location = location,
            category = category,
            createTime = System.currentTimeMillis(),
            status = ReportStatus.SUBMITTED,
            points = null
        )

        prefManager.addReport(report)
        Toast.makeText(this, "报料提交成功！", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
