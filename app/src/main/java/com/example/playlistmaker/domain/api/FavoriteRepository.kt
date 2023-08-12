package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun deleteLike(track: Track)

    fun setLike(track: Track)

    fun getFavoriteTracks() : Flow<List<Track>>

}