package com.example.playlistmaker.data.repository

import android.annotation.SuppressLint
import android.net.Uri
import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.Convertor
import com.example.playlistmaker.data.db.entity.AlbumEntity
import com.example.playlistmaker.data.db.entity.IncludeAlbum
import com.example.playlistmaker.domain.api.AlbumRepository
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    override suspend fun included(album: Album, track: Track): Pair<Boolean, String> {
        return appDataBase.albumDAO().included(track.artworkUrl100, album.UUID) to album.nameAlbum
    }

    override suspend fun removeTrackFromAlbum(UUIDTrack: String, UUIDAlbum: String) {
        appDataBase.albumDAO().deleteIncludedTrack(UUIDTrack, UUIDAlbum)
    }

    override suspend fun deleteAlbum(UUIDAlbum: String) {
        appDataBase.albumDAO().deleteAlbum(UUIDAlbum)
        appDataBase.albumDAO().deleteIncludedTracks(UUIDAlbum)
    }

    override suspend fun updateAlbum(
        nameAlbum: String,
        description: String,
        uri: Uri,
        uid: String
    ) {

        appDataBase.albumDAO().updateAlbum(AlbumEntity(uid, nameAlbum, description, uri.toString()))

    }

    override suspend fun getDataAlbum(UUID: String): Album {
        return convertor.mapAlbum(appDataBase.albumDAO().getDataAlbum(UUID))
    }

    override suspend fun getIncludedTracks(UUID: String): List<Track> {

        val gson = Gson()
        val tracks = kotlin.collections.ArrayList<Track>()
        val type = object : TypeToken<Track>() {}.type

        appDataBase.albumDAO().getIncludedTrack(UUID).forEach {
            tracks.add(gson.fromJson(it.track, type))
        }

        return tracks

    }

    override suspend fun addSongToPlaylist(album: Album, track: Track) {

        val gson = Gson()

        val includeAlbum =
            IncludeAlbum(
                UUID.randomUUID().toString(),
                album.UUID,
                track.artworkUrl100,
                gson.toJson(track)
            )
        appDataBase.albumDAO().insertIncludeAlbum(includeAlbum)
    }
}