package com.example.playlistmaker.presentation.api

import android.net.Uri
import com.example.playlistmaker.domain.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumInteractor {

    suspend fun saveAlbum(nameAlbum: String, description:String, uri: Uri)

    fun getAlbums(): Flow<ArrayList<Album>>

}