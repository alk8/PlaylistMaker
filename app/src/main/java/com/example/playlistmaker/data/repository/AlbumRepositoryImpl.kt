package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.Convertor
import com.example.playlistmaker.data.db.entity.IncludeAlbum
import com.example.playlistmaker.domain.api.AlbumRepository
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import kotlin.collections.ArrayList

class AlbumRepositoryImpl(private val appDataBase: AppDataBase, private val convertor: Convertor): AlbumRepository {
    override suspend fun saveAlbum(album: Album) {
        appDataBase.albumDAO().insertAlbum(convertor.mapAlbum(album))
    }

    override fun getPlaylists(): Flow<ArrayList<Album>> = flow{

        val albums = ArrayList<Album>()
        val albumsEntity = appDataBase.albumDAO().getAlbums()

        // Получить информацию о треках в плейлисте
        val counts = appDataBase.albumDAO().countTracks().forEach {
            var a = 0
        }

        albumsEntity.forEach {
            albums.add(convertor.mapAlbum(it))
        }
        emit(albums)
    }

    override suspend fun addSongToPlaylist(album: Album, track: Track) {
        val includeAlbum = IncludeAlbum(UUID.randomUUID().toString(),album.UUID,track.artworkUrl100)
        appDataBase.albumDAO().insertIncludeAlbum(includeAlbum)
    }
}