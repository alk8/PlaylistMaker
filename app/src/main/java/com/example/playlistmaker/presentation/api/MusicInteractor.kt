package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track

interface MusicInteractor {
    fun prepare(trackUrl:String,completion: () -> Unit,prepared: () -> Unit)
    fun currentPosition(): String
    fun pause()
    fun start()
    fun release()
    fun jsonToTrack(textJSON: String): Track
}