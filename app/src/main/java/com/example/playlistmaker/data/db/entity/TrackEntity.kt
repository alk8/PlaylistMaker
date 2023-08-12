package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class TrackEntity(

    val artworkUrl100: String = "",
    @PrimaryKey
    val trackName: String = "",

    val artistName: String? = null,

    val trackTimeMillis: String? = null,

    val collectionName: String? = null,

    val releaseDate: String? = null,

    val primaryGenreName: String? = null,

    val country: String? = null,

    val previewUrl: String = "",

    val isFavorite: Boolean = false

)
