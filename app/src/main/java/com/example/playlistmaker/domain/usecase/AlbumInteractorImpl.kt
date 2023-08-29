package com.example.playlistmaker.domain.usecase

import android.net.Uri
import com.example.playlistmaker.domain.api.AlbumRepository
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.flow.Flow

class AlbumInteractorImpl(private val albumRepository: AlbumRepository):AlbumInteractor {
    override suspend fun saveAlbum(nameAlbum: String, description: String, uri: Uri) {
        albumRepository.saveAlbum(Album(nameAlbum,description,uri))
    }

    override suspend fun addSongToPlaylist(album: Album, track: Track) {
        albumRepository.addSongToPlaylist(album,track)
    }

    override suspend fun included(album: Album, track: Track): Pair<Boolean,String> {
       return albumRepository.included(album,track)
    }

    override fun getAlbums(): Flow<ArrayList<Album>> {
        return albumRepository.getPlaylists()
    }
}