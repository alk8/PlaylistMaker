package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ActivityMedia : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        val gson = Gson()
        val type = object : TypeToken<Track?>() {}.type
        val track = gson.fromJson<Track>(intent.getStringExtra("track"), type)

        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val time = findViewById<TextView>(R.id.time)
        val album = findViewById<TextView>(R.id.albumData)
        val year = findViewById<TextView>(R.id.yearData)
        val genre = findViewById<TextView>(R.id.genreData)
        val country = findViewById<TextView>(R.id.countryData)
        val picture = findViewById<ImageView>(R.id.album)

        trackName.text = track.trackName
        artistName.text = track.artistName
        time.text = FormatterTime.formatTime(track.trackTimeMillis)
        album.text = track.collectionName
        year.text = FormatterTime.getYear(track.releaseDate)
        genre.text = track.primaryGenreName
        country.text = track.country

        Glide.with(picture).load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")).centerCrop()
            .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
            .into(picture)

        findViewById<ImageView>(R.id.back).setOnClickListener {finish()}

    }
}