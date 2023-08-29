package com.example.playlistmaker.data.repository

import android.annotation.SuppressLint
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

class AlbumRepositoryImpl(private val appDataBase: AppDataBase, private val convertor: Convertor) :
    AlbumRepository {
    override suspend fun saveAlbum(album: Album) {
        appDataBase.albumDAO().insertAlbum(convertor.mapAlbum(album))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getPlaylists(): Flow<ArrayList<Album>> = flow {

        val albums = ArrayList<Album>()
        appDataBase.albumDAO().getAlbumsAndCounts().forEach {
            albums.add(convertor.mapAlbum(it))
        }
        emit(albums)
    }

    override suspend fun included(album: Album, track: Track): Pair<Boolean,String> {
        return appDataBase.albumDAO().included(track.artworkUrl100,album.UUID) to album.nameAlbum
    }

    override suspend fun addSongToPlaylist(album: Album, track: Track) {
        val includeAlbum =
            IncludeAlbum(UUID.randomUUID().toString(), album.UUID, track.artworkUrl100)
        appDataBase.albumDAO().insertIncludeAlbum(includeAlbum)
    }
}