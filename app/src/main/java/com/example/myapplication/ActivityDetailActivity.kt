package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.Locale

class ActivityDetailActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var toolbar: Toolbar
    private lateinit var activityCoverImageView: ImageView
    private lateinit var activityTitleTextView: TextView
    private lateinit var activityDescriptionTextView: TextView
    private lateinit var activityTimeTextView: TextView
    private lateinit var activityLocationTextView: TextView
    private lateinit var activityParticipantTextView: TextView
    private lateinit var activityStatusTextView: TextView
    private lateinit var registerButton: Button

    private var activityItem: ActivityItem? = null

    private val requestOptions = RequestOptions()
        .transform(CenterCrop(), RoundedCorners(8))
        .placeholder(R.drawable.placeholder_news)
        .error(R.drawable.placeholder_news)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        prefManager = PrefManager(this)

        initViews()

        val activityId = intent.getStringExtra("activityId")
        activityId?.let {
            loadActivityData(it)
        } ?: run {
            Toast.makeText(this, "活动不存在", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initViews() {
        activityCoverImageView = findViewById(R.id.activityCoverImageView)
        activityTitleTextView = findViewById(R.id.activityTitleTextView)
        activityDescriptionTextView = findViewById(R.id.activityDescriptionTextView)
        activityTimeTextView = findViewById(R.id.activityTimeTextView)
        activityLocationTextView = findViewById(R.id.activityLocationTextView)
        activityParticipantTextView = findViewById(R.id.activityParticipantTextView)
        activityStatusTextView = findViewById(R.id.activityStatusTextView)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            handleRegistration()
        }
    }

    private fun loadActivityData(activityId: String) {
        val activities = prefManager.getActivities()
        activityItem = activities.find { it.id == activityId }

        activityItem?.let {
            activityTitleTextView.text = it.title
            activityDescriptionTextView.text = it.description
            activityTimeTextView.text = "${dateFormat.format(it.startTime)} - ${dateFormat.format(it.endTime)}"
            activityLocationTextView.text = it.location
            activityParticipantTextView.text = "${it.participantCount}人"

            activityStatusTextView.text = when (it.status) {
                ActivityStatus.UPCOMING -> "即将开始"
                ActivityStatus.ONGOING -> "进行中"
                ActivityStatus.ENDED -> "已结束"
            }

            updateRegisterButton()

            if (it.cover.isNotEmpty()) {
                Glide.with(this)
                    .load(it.cover)
                    .apply(requestOptions)
                    .into(activityCoverImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.placeholder_news)
                    .apply(requestOptions)
                    .into(activityCoverImageView)
            }
        }
    }

    private fun updateRegisterButton() {
        activityItem?.let {
            if (it.isRegistered) {
                registerButton.text = "取消报名"
            } else {
                registerButton.text = "立即报名"
            }
        }
    }

    private fun handleRegistration() {
        activityItem?.let {
            if (it.isRegistered) {
                AlertDialog.Builder(this)
                    .setTitle("取消报名")
                    .setMessage("确定要取消报名参加这个活动吗？")
                    .setPositiveButton("确定") { _, _ ->
                        prefManager.updateActivityRegistration(it.id, false)
                        activityItem = it.copy(isRegistered = false)
                        updateRegisterButton()
                        Toast.makeText(this, "已取消报名", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("确认报名")
                    .setMessage("确定要报名参加这个活动吗？")
                    .setPositiveButton("确定") { _, _ ->
                        prefManager.updateActivityRegistration(it.id, true)
                        activityItem = it.copy(isRegistered = true)
                        updateRegisterButton()
                        Toast.makeText(this, "报名成功", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        }
    }
}