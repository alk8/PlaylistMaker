package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.domain.api.FavoriteRepository
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(private val appDataBase: AppDataBase): FavoriteRepository {
    override fun getLike() {
        TODO("Not yet implemented")
    }

    override fun setLike() {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTracks(): Flow<List<Track>> = flow{
        val tracks = appDataBase.trackDao().getFavoriteTracks()
        emit(tracks)
    }
}