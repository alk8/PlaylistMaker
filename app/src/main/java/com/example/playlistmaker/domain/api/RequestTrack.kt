package com.example.playlistmaker.domain.api
import com.example.playlistmaker.domain.models.Track

interface RequestTrack {

    fun evaluateRequest(text: String?) : List<Track>?

}