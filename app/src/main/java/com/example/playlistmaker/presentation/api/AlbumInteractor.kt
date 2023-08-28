package com.example.playlistmaker.presentation.api

import android.net.Uri

interface AlbumInteractor {

    suspend fun saveAlbum(nameAlbum: String, description:String, uri: Uri){

    }

}