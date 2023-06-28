package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.PlayerMedia
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.MusicInteractor
import org.koin.java.KoinJavaComponent.getKoin

class MusicInteractorImpl(private val musicPlayer : PlayerMedia) : MusicInteractor {

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