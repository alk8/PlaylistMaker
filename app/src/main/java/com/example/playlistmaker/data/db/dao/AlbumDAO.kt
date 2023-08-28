package com.example.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.AlbumEntity

@Dao
interface AlbumDAO {

    @Insert(entity = AlbumEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Query("SELECT * FROM Albums")
    suspend fun getAlbums():List<AlbumEntity>

}