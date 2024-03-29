package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.FavoriteRepository
import com.example.playlistmaker.domain.api.PlayerMedia
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.presentation.api.MusicInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MusicInteractorImpl(
    private val musicPlayer: PlayerMedia,
    override var playerStateFlow: MutableStateFlow<StateMusicPlayer>,
    private val favoriteRepository: FavoriteRepository
) : MusicInteractor {

    override fun prepare(trackUrl: String?) {

        val func = { playerStateFlow.value = StateMusicPlayer.PREPARED }

        if (trackUrl?.isNotEmpty() == true){
            musicPlayer.prepare(trackUrl, func, func)
            playerStateFlow.value = StateMusicPlayer.PREPARED
        }else{
            playerStateFlow.value = StateMusicPlayer.NO_CONTENT
        }
    }

    override fun currentPosition(): String = musicPlayer.currentPosition()

    override fun pause() {
        musicPlayer.pause()
        playerStateFlow.value = StateMusicPlayer.PAUSED
    }

    override fun start() {
        musicPlayer.start()
        playerStateFlow.value = StateMusicPlayer.PLAYING
    }

    override suspend fun setLike(track: Track) {
        favoriteRepository.setLike(track)
    }

    override suspend fun isFavorite(track: Track): Boolean {
        return favoriteRepository.isFavorite(track)
    }

    override fun getFavoriteTracks(): Flow<ArrayList<Track>> {
        return favoriteRepository.getFavoriteTracks()
    }

    override suspend fun deleteLike(track: Track) {
        favoriteRepository.deleteLike(track)
    }

    override fun release() {
        musicPlayer.release()
    }

    override fun jsonToTrack(textJSON: String): Track = musicPlayer.jsonToTrack(textJSON)

}