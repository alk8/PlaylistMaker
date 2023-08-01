package com.example.playlistmaker.data.retrofit

import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track

class AppleAPI(private val appleAPI: SearchAPI) : GettingTracks {

    override suspend fun evaluateRequest(text: String): ArrayList<Track>?{
        try {
            appleAPI.getMusic(text).apply {
                return if (this.results.isNotEmpty()) {
                    (this.results as ArrayList<Track>)
                } else {
                    (arrayListOf())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
