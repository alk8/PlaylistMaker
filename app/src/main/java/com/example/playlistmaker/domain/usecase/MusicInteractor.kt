package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.Player
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.MusicPlayer
import org.koin.java.KoinJavaComponent.getKoin

class MusicInteractor : MusicPlayer {

    private val musicPlayer : Player = getKoin().get()

    override fun prepare(trackUrl: String?, completion: (Any) -> Unit, prepared: (Any) -> Unit) {
        musicPlayer.prepare(trackUrl,completion,prepared)
    }

    override fun currentPosition(): String = musicPlayer.currentPosition()

    override fun pause(){
        musicPlayer.pause()
    }
    override fun start() {
        musicPlayer.start()
    }
    override fun release() {
        musicPlayer.release()
    }

    override fun jsonToTrack(textJSON: String): Track = musicPlayer.jsonToTrack(textJSON)

}