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
        private const val REFRESH_TIME = 400L
        private const val NULL_TIMER = "00:00"
    }

    private var playerState = DEFAULT
    private val gson = Gson()
    private val type = object : TypeToken<Track?>() {}.type
    private val musicPlayer = MediaPlayer()
    private val track = gson.fromJson<Track>(intent.getStringExtra("track"), type)
    private val handler = Handler(Looper.myLooper()!!)
    private val timer = findViewById<TextView>(R.id.timer)
    private val play = findViewById<ImageView>(R.id.playButton)
    private val pauseResourse = if (isDarkTheme()) {
        R.drawable.pause_nightmode
    } else R.drawable.pause

    private val playResourse = if (isDarkTheme()) {
        R.drawable.play_button
    } else R.drawable.white_play_button

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val time = findViewById<TextView>(R.id.time)
        val album = findViewById<TextView>(R.id.albumData)
        val year = findViewById<TextView>(R.id.yearData)
        val genre = findViewById<TextView>(R.id.genreData)
        val country = findViewById<TextView>(R.id.countryData)
        val picture = findViewById<ImageView>(R.id.album)

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
        play.setImageResource(pauseResourse)
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    private fun pausePlayer() {
        musicPlayer.pause()
        playerState = PAUSED
        play.setImageResource(playResourse)
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
        if (playerState == PLAYING) {
            timer.text = FormatterTime.formatTime(musicPlayer.currentPosition.toString())
        }
    }

    private fun stopTimer() = handler.removeCallbacks { refreshTimer() }

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}