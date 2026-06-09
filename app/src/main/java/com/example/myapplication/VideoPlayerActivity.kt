package com.example.myapplication

import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var titleTextView: TextView
    private lateinit var infoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        videoView = findViewById(R.id.video_view)
        titleTextView = findViewById(R.id.video_title)
        infoTextView = findViewById(R.id.video_info)

        val videoUrl = intent.getStringExtra("video_url")
        val newsTitle = intent.getStringExtra("news_title")
        val newsSrc = intent.getStringExtra("news_src")
        val newsTime = intent.getStringExtra("news_time")

        titleTextView.text = newsTitle ?: "视频新闻"
        infoTextView.text = "$newsSrc · $newsTime"

        videoUrl?.let {
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            
            try {
                videoView.setVideoPath(it)
                videoView.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) {
            videoView.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}
