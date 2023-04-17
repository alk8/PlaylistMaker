package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track

interface Serializator {

    fun jsonToTrack(textJSON: String?) : Track

    fun trackToJSON(track: Track): String?

}