package com.example.playlistmaker.presentation.media

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.StateMusicPlayer.*
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MediaActivity : AppCompatActivity() {

    private var isDark = false

    private lateinit var track: Track
    private lateinit var timer: TextView
    private lateinit var play: ImageView
    private val viewModel: MediaViewModel by viewModel{
        parametersOf(intent.getStringExtra("track"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)



        timer = findViewById(R.id.timer)
        play = findViewById(R.id.playButton)
        isDark = isDarkTheme()

        play.setOnClickListener { viewModel.controlPlayer()}
        findViewById<ImageView>(R.id.back).setOnClickListener { finish() }

        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val time = findViewById<TextView>(R.id.time)
        val album = findViewById<TextView>(R.id.albumData)
        val year = findViewById<TextView>(R.id.yearData)
        val genre = findViewById<TextView>(R.id.genreData)
        val country = findViewById<TextView>(R.id.countryData)
        val picture = findViewById<ImageView>(R.id.album)

        //viewModel.preparePlayer()

        viewModel.getTimerTextData().observe(this) {
            timer.text = it
        }

        viewModel.getStateData().observe(this) {

            if (isDark) {
                if (it == PLAYING) {
                    play.setImageResource(R.drawable.pause_nightmode)
                } else {
                    play.setImageResource(R.drawable.white_play_button)
                }

            } else {
                if (it == PLAYING) {
                    play.setImageResource(R.drawable.pause)
                } else {
                    play.setImageResource(R.drawable.play_button)
                }
            }
        }

        viewModel.getTrackData().observe(this) {

            track = it
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
        }

    }

    private fun isDarkTheme(): Boolean {
        return this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

}