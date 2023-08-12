package com.example.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.data.db.dao.TrackDAO
import com.example.playlistmaker.domain.models.Track

@Database(version = 1, entities = [Track::class])
abstract class AppDataBase : RoomDatabase(){
    abstract fun trackDao():TrackDAO
}