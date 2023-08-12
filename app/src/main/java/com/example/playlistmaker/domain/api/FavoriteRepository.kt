package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getLike()

    fun setLike()

    fun getFavoriteTracks() : Flow<List<Track>>

}