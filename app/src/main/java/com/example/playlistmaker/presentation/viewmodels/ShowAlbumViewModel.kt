package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.launch

class ShowAlbumViewModel(private val albumInteractor: AlbumInteractor) : ViewModel() {

    private val album = MutableLiveData<Album>()
    private val tracks = MutableLiveData<List<Track>>()

    fun getAlbumData():LiveData<Album> = album
    fun getTracksData():LiveData<List<Track>> = tracks

    fun getDataAlbum(UUID:String){
        viewModelScope.launch {

            album.value = albumInteractor.getDataAlbum(UUID)
            tracks.value = albumInteractor.getIncludedTracks(UUID)

        }
    }

}