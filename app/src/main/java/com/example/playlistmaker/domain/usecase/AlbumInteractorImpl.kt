package com.example.playlistmaker.domain.usecase

import android.net.Uri
import com.example.playlistmaker.data.db.entity.AlbumEntity
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

    override suspend fun getDataAlbum(UUID: String): Album {
        return albumRepository.getDataAlbum(UUID)
    }

    override suspend fun getIncludedTracks(UUID: String): List<Track> {
        return albumRepository.getIncludedTracks(UUID)
    }

    override suspend fun removeTrackFromAlbum(UUIDTrack: String, UUIDAlbum: String) {
        albumRepository.removeTrackFromAlbum(UUIDTrack,UUIDAlbum)
    }

    override fun getAlbums(): Flow<ArrayList<Album>> {
        return albumRepository.getPlaylists()
    }
}