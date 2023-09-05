package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.Convertor
import com.example.playlistmaker.domain.api.FavoriteRepository
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(private val appDataBase: AppDataBase,private val convertor: Convertor): FavoriteRepository {
    override suspend fun deleteLike(track: Track) {
        appDataBase.trackDao().deleteTrack(convertor.map(track))
    }

    override suspend fun setLike(track: Track) {

        val trackEntity = convertor.map(track)
        appDataBase.trackDao().insertTrack(trackEntity)
    }

    override suspend fun isFavorite(track: Track): Boolean {
        return appDataBase.trackDao().isFavorite(track.artworkUrl100)
    }

    override fun getFavoriteTracks(): Flow<ArrayList<Track>> = flow{
        val tracksEntity = appDataBase.trackDao().getFavoriteTracks()

        val tracks = ArrayList<Track>()

        tracksEntity.forEach {
            tracks.add(convertor.map(it))
        }

        emit(tracks)
    }
}