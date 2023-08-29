package com.example.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.AlbumEntity
import com.example.playlistmaker.data.db.entity.IncludeAlbum
import com.example.playlistmaker.data.db.queries.QueryAlbumGroup

@Dao
interface AlbumDAO {

    @Insert(entity = AlbumEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Query("SELECT * FROM Albums")
    suspend fun getAlbums():List<AlbumEntity>

    // Добавить трек в альбом
    @Insert(entity = IncludeAlbum::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncludeAlbum(includeAlbum: IncludeAlbum)

    // Узнать есть ли трек в альбоме
    @Query("SELECT EXISTS (SELECT 1 FROM IncludeAlbums WHERE UUIDTrack LIKE :UUIDTrack)")
    suspend fun isFavorite(UUIDTrack:String) : Boolean

    // Получить количество треков в альбомах
    @Query("SELECT COUNT(UUIDTrack) AS countTracks, UUIDAlbum FROM IncludeAlbums GROUP BY UUIDAlbum")
    suspend fun countTracks():List<QueryAlbumGroup>

}