package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IncludeAlbums")
data class IncludeAlbum(
    @PrimaryKey
    val UUID:String,
    val UUIDAlbum:String = "",
    val UUIDTrack:String = "",
    val track:String = "",
    val date: Long = 0L
)
