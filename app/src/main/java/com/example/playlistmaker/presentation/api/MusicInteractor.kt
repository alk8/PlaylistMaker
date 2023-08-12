package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow

interface MusicInteractor {
    val playerStateFlow: MutableStateFlow<StateMusicPlayer>
    fun prepare(trackUrl: String?)
    fun currentPosition(): String
    fun pause()
    fun start()
    fun release()
    fun jsonToTrack(textJSON: String): Track
    suspend fun setLike(track:Track)
    suspend fun deleteLike(track: Track)
    suspend fun isFavorite(track: Track) : Boolean

}