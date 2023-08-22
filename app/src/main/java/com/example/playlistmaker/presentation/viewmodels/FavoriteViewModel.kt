package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.api.TracksInteracator

import kotlinx.coroutines.launch

class FavoriteViewModel(private val musicInteractor: MusicInteractor,private val tracksInteracor:TracksInteracator) : ViewModel(){

    private var favorite = MutableLiveData<ArrayList<Track>>()

    fun getFavoriteTracks(): LiveData<ArrayList<Track>> {

        viewModelScope.launch {
            musicInteractor.getFavoriteTracks().collect{
                favorite.value = it
            }
        }

        return favorite
    }

    fun trackToJSON(track: Track): String? {
       return tracksInteracor.trackToJSON(track)
    }
}
