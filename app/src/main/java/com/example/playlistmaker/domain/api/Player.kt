package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface Player {
    fun prepare(trackUrl:String?,completion: (Any) -> Unit,prepared: (Any) -> Unit)
    fun currentPosition(): String
    fun pause()
    fun start()
    fun release()
    fun jsonToTrack(textJSON: String): Track
}