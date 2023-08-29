package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.entities.FormatterTime
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.AlbumInteractor
import com.example.playlistmaker.presentation.api.MusicInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val musicPlayer: MusicInteractor,
    val text: String?,
    private val albumInteractor: AlbumInteractor
) : ViewModel() {

    companion object {
        private const val REFRESH_TIME = 300L
        private const val NULL_TIMER = "00:00"
    }

    private var track = MutableLiveData<Track>()
    private var timerText = MutableLiveData<String>()
    private var state = MutableLiveData<StateMusicPlayer>()
    private var playlists = MutableLiveData<ArrayList<Album>>()
    private var refresh: Job

    fun getTrackData(): LiveData<Track> = track
    fun getTimerTextData(): LiveData<String> = timerText
    fun getStateData(): LiveData<StateMusicPlayer> = state
    fun getPlaylistData(): LiveData<ArrayList<Album>> = playlists

    init {

        timerText.value = NULL_TIMER

        viewModelScope.launch {
            musicPlayer.playerStateFlow.collect{
                state.value = it
            }
        }

        if (!text.isNullOrEmpty()) {

            val preparedTrack = musicPlayer.jsonToTrack(text)

            viewModelScope.launch {
                val isFavorite = musicPlayer.isFavorite(preparedTrack)
                preparedTrack.isFavorite = isFavorite
                track.value = preparedTrack
                val path = track.value?.previewUrl
                musicPlayer.prepare(path)
            }
        }

        refresh = viewModelScope.launch {
            while (true) {
                refreshTimer()
                delay(REFRESH_TIME)
            }
        }
    }

    private fun refreshTimer() = timerText.postValue(FormatterTime.formatTime(musicPlayer.currentPosition()))

    fun controlPlayer() {

        when (state.value) {
            StateMusicPlayer.PLAYING -> {
                pausePlayer()
            }
            StateMusicPlayer.PAUSED, StateMusicPlayer.PREPARED, StateMusicPlayer.DEFAULT -> {
                startPlayer()
            }
            else -> {}
        }
    }

    private fun pausePlayer() {
        musicPlayer.pause()
    }

    private fun startPlayer() {
        musicPlayer.start()
        refresh.start()
    }

    fun setLike(){

        viewModelScope.launch {
            musicPlayer.setLike(track.value!!)
        }


        track.value!!.isFavorite = true
    }

    fun deleteLike(){

        viewModelScope.launch {
            musicPlayer.deleteLike(track.value!!)
        }

        track.value!!.isFavorite = false
    }

    fun getPlaylists(){
        viewModelScope.launch {
            albumInteractor.getAlbums().collect{
                playlists.value = it
            }
        }
    }

    fun addSongToPlaylist(album:Album,track: Track){
        viewModelScope.launch {
            albumInteractor.addSongToPlaylist(album, track)
        }
    }
}