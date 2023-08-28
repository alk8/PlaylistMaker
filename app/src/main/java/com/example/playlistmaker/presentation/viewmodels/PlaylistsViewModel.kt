package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val albumInteractor: AlbumInteractor): ViewModel() {

    private var playlists = MutableLiveData<ArrayList<Album>>()

    fun getPlaylists(): LiveData<ArrayList<Album>> {

        viewModelScope.launch {
            albumInteractor.getAlbums().collect{
                playlists.value = it
            }
        }
        return playlists
    }
}