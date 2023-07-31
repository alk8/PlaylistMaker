package com.example.playlistmaker.data.retrofit

import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppleAPI(private val appleAPI: SearchAPI) : GettingTracks {

    override suspend fun evaluateRequest(text: String): Flow<ArrayList<Track>?> = flow {
        try {
            appleAPI.getMusic(text).apply {
                if (this.results.isNotEmpty()) {
                    val tracks = this.results
                    emit(tracks as ArrayList<Track>)
                } else {
                    emit(arrayListOf())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }

    }
}
