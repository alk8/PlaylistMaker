package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.TrackConvertor
import com.example.playlistmaker.domain.api.FavoriteRepository
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(private val appDataBase: AppDataBase,private val trackConvertor: TrackConvertor): FavoriteRepository {
    override fun deleteLike(track: Track) {
        appDataBase.trackDao().deleteTrack(trackConvertor.map(track))
    }

    override fun setLike(track: Track) {

        val trackEntity = trackConvertor.map(track)

        appDataBase.trackDao().insertTrack(trackEntity)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> = flow{
        val tracksEntity = appDataBase.trackDao().getFavoriteTracks()

        val tracks = ArrayList<Track>()

        tracksEntity.forEach {
            tracks.add(trackConvertor.map(it))
        }

        emit(tracks)
    }
}