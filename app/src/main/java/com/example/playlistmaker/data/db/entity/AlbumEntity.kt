package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Albums")
data class AlbumEntity(
    @PrimaryKey
    val UUID:String,
    val nameAlbum:String,
    val description:String,
    val uri: String
)