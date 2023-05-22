package com.example.playlistmaker.presentation.viewmodels

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.repository.SerializatorTrack
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.domain.models.StateMusicPlayer
import com.example.playlistmaker.domain.models.Track

class MediaViewModel(val text: String?): ViewModel(){

    companion object {
        private const val REFRESH_TIME = 1000L
        private const val NULL_TIMER = "00:00"
    }

    var track = MutableLiveData<Track>()
    var timerText = MutableLiveData<String>()
    var state = MutableLiveData<StateMusicPlayer>()
    private var handler: Handler

    private val musicPlayer = MediaPlayer()

    init {
        track.value = SerializatorTrack().jsonToTrack(text)
        timerText.value = NULL_TIMER
        state.value = StateMusicPlayer.DEFAULT
        handler = Handler(Looper.myLooper()!!)
    }

    fun preparePlayer(){
        musicPlayer.setDataSource(track.value?.previewUrl)
        musicPlayer.prepareAsync()
        musicPlayer.setOnCompletionListener {
            state.value = StateMusicPlayer.PREPARED
            stopTimer()
            timerText.value = NULL_TIMER
        }
        musicPlayer.setOnPreparedListener { state.value = StateMusicPlayer.PREPARED }
    }

    private fun stopTimer() = handler.removeCallbacks { refreshTimer() }

    private fun refreshTimer() {
        if (state.value != StateMusicPlayer.PAUSED) {
            timerText.postValue(FormatterTime.formatTime(musicPlayer.currentPosition.toString()))
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
            null -> TODO()
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

    fun onDestroy(){
        stopTimer()
        musicPlayer.release()
    }
}