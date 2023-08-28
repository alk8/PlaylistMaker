package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun saveAlbum(album:Album)
    fun getPlaylists() : Flow<ArrayList<Album>>
}