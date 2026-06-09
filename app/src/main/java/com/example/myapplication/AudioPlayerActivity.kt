package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var audioTitle: TextView
    private lateinit var audioInfo: TextView
    private lateinit var audioSeekbar: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView
    private lateinit var btnPlay: ImageView
    private lateinit var btnRewind: ImageView
    private lateinit var btnForward: ImageView

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    audioSeekbar.progress = it.currentPosition
                    currentTime.text = formatTime(it.currentPosition)
                    handler.postDelayed(this, 100)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        audioTitle = findViewById(R.id.audio_title)
        audioInfo = findViewById(R.id.audio_info)
        audioSeekbar = findViewById(R.id.audio_seekbar)
        currentTime = findViewById(R.id.current_time)
        totalTime = findViewById(R.id.total_time)
        btnPlay = findViewById(R.id.btn_play)
        btnRewind = findViewById(R.id.btn_rewind)
        btnForward = findViewById(R.id.btn_forward)

        val audioUrl = intent.getStringExtra("audio_url")
        val newsTitle = intent.getStringExtra("news_title")
        val newsSrc = intent.getStringExtra("news_src")
        val newsTime = intent.getStringExtra("news_time")

        audioTitle.text = newsTitle ?: "音频新闻"
        audioInfo.text = "$newsSrc · $newsTime"

        audioUrl?.let { initMediaPlayer(it) }

        btnPlay.setOnClickListener { togglePlay() }
        btnRewind.setOnClickListener { rewind() }
        btnForward.setOnClickListener { forward() }

        audioSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initMediaPlayer(url: String) {
        try {
            mediaPlayer = MediaPlayer().also { mp ->
                mp.setDataSource(url)
                mp.prepareAsync()
                mp.setOnPreparedListener {
                    audioSeekbar.max = mp.duration
                    totalTime.text = formatTime(mp.duration)
                    mp.start()
                    isPlaying = true
                    updatePlayButton()
                    handler.post(updateRunnable)
                }
                mp.setOnCompletionListener {
                    isPlaying = false
                    updatePlayButton()
                    mp.seekTo(0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun togglePlay() {
        mediaPlayer?.let {
            if (isPlaying) {
                it.pause()
                isPlaying = false
            } else {
                it.start()
                isPlaying = true
                handler.post(updateRunnable)
            }
            updatePlayButton()
        }
    }

    private fun rewind() {
        mediaPlayer?.let {
            val newPosition = (it.currentPosition - 10000).coerceAtLeast(0)
            it.seekTo(newPosition)
            audioSeekbar.progress = newPosition
            currentTime.text = formatTime(newPosition)
        }
    }

    private fun forward() {
        mediaPlayer?.let {
            val newPosition = (it.currentPosition + 10000).coerceAtMost(it.duration)
            it.seekTo(newPosition)
            audioSeekbar.progress = newPosition
            currentTime.text = formatTime(newPosition)
        }
    }

    private fun updatePlayButton() {
        if (isPlaying) {
            btnPlay.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            btnPlay.setImageResource(android.R.drawable.ic_media_play)
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
        return sdf.format(Date(milliseconds.toLong()))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPlaying = false
                updatePlayButton()
            }
        }
        handler.removeCallbacks(updateRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(updateRunnable)
    }
}
