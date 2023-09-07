package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun saveAlbum(album:Album)
    fun getPlaylists() : Flow<ArrayList<Album>>
    suspend fun addSongToPlaylist(album:Album,track: Track)
    suspend fun included(album: Album, track: Track):Pair<Boolean,String>
    suspend fun getDataAlbum(UUID:String): Album
    suspend fun getIncludedTracks(UUID:String):List<Track>
    suspend fun removeTrackFromAlbum(UUIDTrack:String,UUIDAlbum: String)
}