package com.example.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.AlbumEntity
import com.example.playlistmaker.data.db.entity.IncludeAlbum
import com.example.playlistmaker.data.db.queries.QueryAlbumGroup
import com.example.playlistmaker.data.db.queries.QueryTracks

@Dao
interface AlbumDAO {

    // Добавить альбом
    @Insert(entity = AlbumEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumEntity: AlbumEntity)

    // Добавить трек в альбом
    @Insert(entity = IncludeAlbum::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun insertIncludeAlbum(includeAlbum: IncludeAlbum)

    // Узнать есть ли трек в альбоме
    @Query("SELECT EXISTS (SELECT 1 FROM IncludeAlbums WHERE UUIDTrack LIKE :UUIDTrack AND UUIDAlbum LIKE :UUIDAlbum)")
    suspend fun included(UUIDTrack: String, UUIDAlbum: String): Boolean

    // Собрать информацию об альбомах
    @Query("SELECT alm.UUID,NameAlbum,description,uri, COUNT(UUIDTrack) as countTracks  FROM Albums alm LEFT JOIN IncludeAlbums inc ON alm.UUID = inc.UUIDAlbum GROUP BY  alm.UUID,NameAlbum,description,uri")
    suspend fun getAlbumsAndCounts(): List<QueryAlbumGroup>

    @Query("SELECT * FROM Albums WHERE UUID LIKE :UUID")
    suspend fun getDataAlbum(UUID: String): AlbumEntity

    @Query("SELECT track FROM IncludeAlbums  WHERE UUIDAlbum LIKE :UUIDAlbum")
    suspend fun getIncludedTrack(UUIDAlbum: String): List<QueryTracks>

    @Query("DELETE FROM IncludeAlbums WHERE UUIDAlbum LIKE :UUIDAlbum AND UUIDTrack LIKE :UUIDTrack")
    suspend fun deleteIncludedTrack(UUIDTrack: String,UUIDAlbum: String)

    @Query("DELETE FROM IncludeAlbums WHERE UUIDAlbum LIKE :UUIDAlbum")
    suspend fun deleteIncludedTracks(UUIDTrack: String)

    @Query("DELETE FROM Albums WHERE UUID LIKE :UUIDAlbum")
    suspend fun deleteAlbum(UUIDTrack: String)

}