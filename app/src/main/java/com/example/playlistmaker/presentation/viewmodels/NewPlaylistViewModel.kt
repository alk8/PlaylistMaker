package com.example.playlistmaker.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.launch

class NewPlaylistViewModel(private val albumInteractor: AlbumInteractor) : ViewModel() {


    val album = MutableLiveData<Album>()
    fun getAlbumData(): LiveData<Album> = album

    fun getAlbum(UUIDAlbum: String) {

        viewModelScope.launch {
            album.value = albumInteractor.getDataAlbum(UUIDAlbum)
        }

    }

    fun saveAlbum(nameAlbum: String, description: String, uri: Uri) {
        viewModelScope.launch { albumInteractor.saveAlbum(nameAlbum, description, uri) }
    }

    fun updateAlbum(nameAlbum: String, description: String, uri: Uri,uid:String?){
        if (uid != null){
            viewModelScope.launch { albumInteractor.updateAlbum(nameAlbum,description,uri,uid) }
        }
    }
}