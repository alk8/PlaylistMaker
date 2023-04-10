package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.StateMusicPlayer.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivityMedia : AppCompatActivity() {

    companion object {
        private const val REFRESH_TIME = 1000L
        private const val NULL_TIMER = "00:00"
    }

    private var playerState = DEFAULT
    private val gson = Gson()

    private val musicPlayer = MediaPlayer()
    private val handler = Handler(Looper.myLooper()!!)
    private var isDark = false

    private lateinit var track: Track
    private lateinit var timer: TextView
    private lateinit var play: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        val type = object : TypeToken<Track?>() {}.type
        track = gson.fromJson(intent.getStringExtra("track"), type)

        timer = findViewById(R.id.timer)
        play = findViewById(R.id.playButton)


        isDark = isDarkTheme()

        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val time = findViewById<TextView>(R.id.time)
        val album = findViewById<TextView>(R.id.albumData)
        val year = findViewById<TextView>(R.id.yearData)
        val genre = findViewById<TextView>(R.id.genreData)
        val country = findViewById<TextView>(R.id.countryData)
        val picture = findViewById<ImageView>(R.id.album)
        timer.text = NULL_TIMER

        play.setOnClickListener { controlPlayer() }

        trackName.text = track.trackName
        artistName.text = track.artistName
        time.text = FormatterTime.formatTime(track.trackTimeMillis)
        album.text = track.collectionName
        year.text = FormatterTime.getYear(track.releaseDate)
        genre.text = track.primaryGenreName
        country.text = track.country

        Glide.with(picture).load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .centerCrop()
            .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
            .into(picture)

        findViewById<ImageView>(R.id.back).setOnClickListener { finish() }
        preparePlayer()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        musicPlayer.release()
    }

    private fun preparePlayer() {
        musicPlayer.setDataSource(track.previewUrl)
        musicPlayer.prepareAsync()
        musicPlayer.setOnCompletionListener {
            playerState = PREPARED
            stopTimer()
            timer.text = NULL_TIMER
        }
        musicPlayer.setOnPreparedListener { playerState = PREPARED }
    }

    private fun startPlayer() {
        musicPlayer.start()
        playerState = PLAYING

        if (isDark) {
            play.setImageResource(R.drawable.pause_nightmode)
        } else play.setImageResource(R.drawable.pause)

        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    private fun pausePlayer() {
        musicPlayer.pause()
        playerState = PAUSED
        if (isDark) {
            play.setImageResource(R.drawable.play_button)
        } else play.setImageResource(R.drawable.white_play_button)
        stopTimer()
    }

    private fun controlPlayer() {
        when (playerState) {
            PLAYING -> {
                pausePlayer()
            }
            PAUSED, PREPARED, DEFAULT -> {
                startPlayer()
            }
        }
    }

    private fun refreshTimer() {
        if (playerState != PAUSED) {
            timer.text = FormatterTime.formatTime(musicPlayer.currentPosition.toString())
        }
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    private fun stopTimer() = handler.removeCallbacks { refreshTimer() }

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}