package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GettingTracks {
    suspend fun evaluateRequest(text: String): Flow<ArrayList<Track>?>
}