package com.example.playlistmaker.domain.usecase

import android.net.Uri
import com.example.playlistmaker.domain.api.AlbumRepository
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.presentation.api.AlbumInteractor

class AlbumInteractorImpl(private val albumRepository: AlbumRepository):AlbumInteractor {
    override suspend fun saveAlbum(nameAlbum: String, description: String, uri: Uri) {
        albumRepository.saveAlbum(Album(nameAlbum,description,uri))
    }
}