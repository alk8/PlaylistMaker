package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.MusicInteractor

import kotlinx.coroutines.launch

class FavoriteViewModel(private val musicInteractor: MusicInteractor) : ViewModel(){

    fun getFavoriteTracks(): ArrayList<Track> {

        var favorite: ArrayList<Track> = ArrayList()
        viewModelScope.launch {
            musicInteractor.getFavoriteTracks().collect{
                favorite = it
            }
        }

        return favorite
    }

}
