package com.example.playlistmaker.data.db.dao

import androidx.room.*
import com.example.playlistmaker.domain.models.Track


@Dao
interface TrackDAO {


    @Insert(entity = Track::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(trackEntity:Track)

    @Delete(entity = Track::class)
    fun deleteTrack(trackEntity:Track)

    @Query("SELECT * FROM Favorite_tracks")
    fun getFavoriteTracks() : List<Track>

}