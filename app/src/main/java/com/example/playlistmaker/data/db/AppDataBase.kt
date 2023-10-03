package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.db.dao.AlbumDAO
import com.example.playlistmaker.data.db.dao.TrackDAO
import com.example.playlistmaker.data.db.entity.AlbumEntity
import com.example.playlistmaker.data.db.entity.IncludeAlbum
import com.example.playlistmaker.data.db.entity.TrackEntity

@Database(version = 9, entities = [TrackEntity::class,AlbumEntity::class,IncludeAlbum::class], exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun trackDao():TrackDAO
    abstract fun albumDAO():AlbumDAO
}