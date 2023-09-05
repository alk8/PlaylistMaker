package com.example.playlistmaker.presentation.api

import android.net.Uri
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface AlbumInteractor {

    suspend fun saveAlbum(nameAlbum: String, description:String, uri: Uri)

    fun getAlbums(): Flow<ArrayList<Album>>

    suspend fun addSongToPlaylist(album:Album,track:Track)

    suspend fun included(album: Album, track: Track): Pair<Boolean,String>

}