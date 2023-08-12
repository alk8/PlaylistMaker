package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.db.dao.TrackDAO
import com.example.playlistmaker.data.db.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class])
abstract class AppDataBase : RoomDatabase(){
    abstract fun trackDao():TrackDAO
}