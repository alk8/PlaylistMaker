package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.Convertor
import com.example.playlistmaker.domain.api.AlbumRepository
import com.example.playlistmaker.domain.models.Album

class AlbumRepositoryImpl(private val appDataBase: AppDataBase, private val convertor: Convertor): AlbumRepository {
    override suspend fun saveAlbum(album: Album) {
        appDataBase.albumDAO().insertAlbum(convertor.mapAlbum(album))
    }
}