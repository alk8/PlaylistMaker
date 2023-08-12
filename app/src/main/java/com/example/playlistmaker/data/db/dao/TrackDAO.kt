package com.example.playlistmaker.data.db.dao

import androidx.room.*
import com.example.playlistmaker.data.db.entity.TrackEntity

@Dao
interface TrackDAO {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity:TrackEntity)

    @Delete(entity = TrackEntity::class)
    suspend fun deleteTrack(trackEntity:TrackEntity)

    @Query("SELECT * FROM Favor")
    suspend fun getFavoriteTracks() : List<TrackEntity>

    @Query("SELECT * FROM Favor WHERE artworkUrl100 = :artworkUrl100")
    suspend fun isFavorite(artworkUrl100:String) : List<TrackEntity>

}