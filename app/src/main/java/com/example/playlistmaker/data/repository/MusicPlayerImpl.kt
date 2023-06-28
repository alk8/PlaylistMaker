package com.example.playlistmaker.data.repository

import android.media.MediaPlayer
import com.example.playlistmaker.domain.api.PlayerMedia
import com.example.playlistmaker.domain.api.Serializator
import com.example.playlistmaker.domain.models.Track

class MusicPlayerImpl(
    private val mediaPlayer: MediaPlayer,
    private val serializatorTrack: Serializator
) : PlayerMedia {

    override fun prepare(trackUrl: String?, completion: (Any) -> Unit, prepared: (Any) -> Unit) {
        mediaPlayer.setDataSource(trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnCompletionListener { completion }
        mediaPlayer.setOnPreparedListener { prepared }
    }

    override fun currentPosition(): String = mediaPlayer.currentPosition.toString()

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun jsonToTrack(textJSON: String): Track = serializatorTrack.jsonToTrack(textJSON)

}