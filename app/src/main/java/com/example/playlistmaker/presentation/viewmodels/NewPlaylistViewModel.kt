package com.example.playlistmaker.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.launch

class NewPlaylistViewModel(private val albumInteractor: AlbumInteractor) : ViewModel() {

    fun saveAlbum(nameAlbum: String, description:String,uri: Uri){
        viewModelScope.launch { albumInteractor.saveAlbum(nameAlbum,description,uri) }
    }

}