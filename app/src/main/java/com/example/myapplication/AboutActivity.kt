package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AboutActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var tvVersion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initViews()
        setupToolbar()
        setupVersionName()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tvVersion = findViewById(R.id.tvVersion)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupVersionName() {
        tvVersion.text = "版本 ${getVersionName()}"
    }

    private fun getVersionName(): String {
        return try {
            packageManager.getPackageInfo(packageName, 0).versionName ?: "1.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0"
        }
    }
}
