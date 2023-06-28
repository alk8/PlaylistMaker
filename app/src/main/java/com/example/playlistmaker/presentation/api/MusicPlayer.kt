package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track

interface MusicPlayer {
    fun prepare(trackUrl:String?,completion: (Any) -> Unit,prepared: (Any) -> Unit)
    fun currentPosition(): String
    fun pause()
    fun start()
    fun release()
    fun jsonToTrack(textJSON: String): Track
}