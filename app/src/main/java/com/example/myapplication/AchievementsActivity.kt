package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AchievementsActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var totalPoints: TextView
    private lateinit var unlockedCount: TextView
    private lateinit var totalCount: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        prefManager = PrefManager(this)

        initViews()
        loadAchievements()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.achievementsRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        totalPoints = findViewById(R.id.totalPoints)
        unlockedCount = findViewById(R.id.unlockedCount)
        totalCount = findViewById(R.id.totalCount)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadAchievements() {
        val achievements = prefManager.getAchievements()

        if (achievements.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
            return
        }

        recyclerView.visibility = android.view.View.VISIBLE
        emptyView.visibility = android.view.View.GONE

        val adapter = AchievementAdapter(achievements)
        recyclerView.adapter = adapter

        updateStats(achievements)
    }

    private fun updateStats(achievements: List<AchievementItem>) {
        val unlockedAchievements = achievements.filter { it.isUnlocked }
        val totalPointsValue = unlockedAchievements.sumOf { it.points }

        totalPoints.text = totalPointsValue.toString()
        unlockedCount.text = unlockedAchievements.size.toString()
        totalCount.text = achievements.size.toString()
    }
}
