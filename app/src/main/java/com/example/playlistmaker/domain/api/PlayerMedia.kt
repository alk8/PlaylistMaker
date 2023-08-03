package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface PlayerMedia {
    fun prepare(trackUrl: String?, completion: () -> Unit, prepared: () -> Unit)
    fun currentPosition(): String
    fun pause()
    fun start()
    fun release()
    fun jsonToTrack(textJSON: String): Track
}