package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Album

interface AlbumRepository {
    suspend fun saveAlbum(album:Album)
}