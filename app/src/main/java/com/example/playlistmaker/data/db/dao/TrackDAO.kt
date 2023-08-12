package com.example.playlistmaker.data.db.dao

import androidx.room.*
import com.example.playlistmaker.data.db.entity.TrackEntity

@Dao
interface TrackDAO {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(trackEntity:TrackEntity)

    @Delete(entity = TrackEntity::class)
    fun deleteTrack(trackEntity:TrackEntity)

    @Query("SELECT * FROM Favorite")
    fun getFavoriteTracks() : List<TrackEntity>

}