package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun isFavorite(track: Track): Boolean

    suspend fun deleteLike(track: Track)

    suspend fun setLike(track: Track)

    fun getFavoriteTracks() : Flow<ArrayList<Track>>

}