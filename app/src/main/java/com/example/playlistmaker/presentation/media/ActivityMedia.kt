package com.example.playlistmaker.presentation.media

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.MediaViewModelFactory


class ActivityMedia : AppCompatActivity() {

    private var isDark = false

    private lateinit var track: Track
    private lateinit var timer: TextView
    private lateinit var play: ImageView

    private lateinit var viewModel: MediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        viewModel = ViewModelProvider(
            this,
            MediaViewModelFactory(intent.getStringExtra("track"))
        )[MediaViewModel::class.java]

        timer = findViewById(R.id.timer)
        play = findViewById(R.id.playButton)
        isDark = isDarkTheme()

        viewModel.getTrackData().observe(this) {
            track = it
        }

        // Для первой инициализации
        track = viewModel.getTrackData().value!!

        viewModel.getTimerTextData().observe(this) {
            timer.text = it
        }

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

    private fun preparePlayer() {
        viewModel.preparePlayer()
    }

    private fun controlPlayer() = viewModel.controlPlayer()

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

}