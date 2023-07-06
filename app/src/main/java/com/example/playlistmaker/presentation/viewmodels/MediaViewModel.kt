package com.example.playlistmaker.presentation.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.MusicInteractor

class MediaViewModel(
    private val musicPlayer: MusicInteractor,
    private var handler: Handler,
    val text: String?,
    private val toast: () -> Unit
) : ViewModel() {

    companion object {
        private const val REFRESH_TIME = 1000L
        private const val NULL_TIMER = "00:00"
    }

    private var track = MutableLiveData<Track>()
    private var timerText = MutableLiveData<String>()
    private var state = MutableLiveData<StateMusicPlayer>()

    fun getTrackData(): LiveData<Track> = track
    fun getTimerTextData(): LiveData<String> = timerText
    fun getStateData(): LiveData<StateMusicPlayer> = state

    init {
        if (!text.isNullOrEmpty()) {
            track.value = musicPlayer.jsonToTrack(text)
        }
        timerText.value = NULL_TIMER
        state.value = StateMusicPlayer.DEFAULT
        val path = track.value?.previewUrl

        if (!path.isNullOrEmpty()) {
            musicPlayer.prepare(path, {
                state.value = StateMusicPlayer.PREPARED
                stopTimer()
                timerText.value = NULL_TIMER
            }, { state.value = StateMusicPlayer.PREPARED })
        }else{
            state.value = StateMusicPlayer.NO_CONTENT
        }
    }

    private fun stopTimer() = handler.removeCallbacks { refreshTimer() }

    private fun refreshTimer() {
        if (state.value != StateMusicPlayer.PAUSED) {
            timerText.postValue(FormatterTime.formatTime(musicPlayer.currentPosition()))
        }
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

    fun controlPlayer() {
        when (state.value) {
            StateMusicPlayer.PLAYING -> {
                pausePlayer()
            }
            StateMusicPlayer.PAUSED, StateMusicPlayer.PREPARED, StateMusicPlayer.DEFAULT -> {
                startPlayer()
            }
            StateMusicPlayer.NO_CONTENT -> {
                toast.invoke()
            }

            else -> {}
        }
    }

    fun pausePlayer() {
        musicPlayer.pause()
        state.value = StateMusicPlayer.PAUSED
        stopTimer()
    }

    private fun startPlayer() {
        musicPlayer.start()
        state.value = StateMusicPlayer.PLAYING
        handler.postDelayed({ refreshTimer() }, REFRESH_TIME)
    }

}